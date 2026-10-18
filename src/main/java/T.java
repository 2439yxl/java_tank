import java.awt.Frame;

public class T {
    public static void main(String[] args) {
        Frame f  = new Frame();
        f.setSize(800,600);
        f.setResizable(false);
        f.setTitle("tank war");
        f.setVisible(true);

        f.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}