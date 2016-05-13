package pokemon.todospokemons;

import static pokemon.todospokemons.RunTodosPokes.progressBar;

/**
 *
 * @author marcelopaglione
 */
public class Buffer {

    private int buffer = 0;

    private boolean ocupado = true;

    public synchronized void set(int value) {
        while (ocupado) {
            try {
                wait();
            } catch (InterruptedException exception) {
            }
        }
        buffer = value;// + get();
        progressBar.setValue(buffer);
        float val;
        val = value * (float) 100 / progressBar.getMaximum();
        progressBar.setString(val + "%");
        if (progressBar.getValue() == progressBar.getMaximum() - 1) {
            progressBar.setVisible(false);
        }
        ocupado = true;
        notify();
    }

    public synchronized int get() {
        while (!ocupado) {
            try {
                wait();
            } catch (InterruptedException exception) {
            }
        }
        ocupado = false;
        int readValue = buffer;
        set(readValue + 1);
        notify();
        return readValue;
    }
}
