package config;

/*
 * Copyright (C) 2016 Paglione
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
import entity.Imagem;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.System.in;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcelo Ortiz Paglione Junior
 */
public class PreencherPokemons implements Runnable {

    private Robot robot;
    private Imagem[] tab;
    private int count;

    public PreencherPokemons() throws AWTException {
        robot = new Robot();
        count = 0;
    }

    public String buildString() {
        String string = "";
        for (Imagem tab1 : tab) {
            string += tab1.getMensagem();
        }
        return string;
    }

    public void setTab(Imagem[] tab) {
        this.tab = tab;
    }

    public void type() throws IOException {
        String s = buildString();
        byte[] bytes = s.getBytes();

        robot.delay(100);
        System.out.println(System.getProperty("user.dir") + "\\..\\lib\\calc.vbs");
        System.out.println(System.getProperty("user.dir") + "..\\lib\\calc.vbs");
        System.out.println(System.getProperty("user.dir") + "\\lib\\calc.vbs");
        //alt_tab();
        Runtime.getRuntime().exec("wscript " + System.getProperty("user.home") + File.separator + "Shuffle-Move" + File.separator + "calc.vbs");
        robot.delay(100);
        control_enter();
        tab();

        for (byte b : bytes) {
            int code = b;
            if (code > 96 && code < 123) {
                code = code - 32;
            }
            robot.delay(10);
            robot.keyPress(code);
            robot.keyRelease(code);
        }

        tab();
        //alt_tab();
        robot.delay(100);
//        URL fileURL = this.getClass().getResource("/config/gen.vbs");
//        Runtime.getRuntime().exec("wscript " + fileURL.toString());
        Runtime.getRuntime().exec("wscript " + System.getProperty("user.home") + File.separator + "Shuffle-Move" + File.separator + "gen.vbs");
        robot.delay(100);
    }

    public void tab() {
        robot.delay(100);
        robot.keyPress(KeyEvent.VK_TAB);
        robot.delay(100);
        robot.keyRelease(KeyEvent.VK_TAB);
        robot.delay(100);
    }

    public void control_enter() {
        if (count > 0) {
            robot.delay(100);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_ENTER);
        }
        count++;
    }

    public void alt_tab() {
        if (System.getProperty("os.name").contains("Mac OS X")) {
            robot.delay(100);
            robot.keyPress(KeyEvent.VK_META);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.delay(50);
            robot.keyRelease(KeyEvent.VK_META);
            robot.keyRelease(KeyEvent.VK_TAB);
        } else {
            robot.delay(100);
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_ALT);

        }
    }

    @Override
    public void run() {
        try {
            type();
        } catch (IOException ex) {
            Logger.getLogger(PreencherPokemons.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
