import controller.PoliticoController;
import model.ListaPoliticos;
import view.VistaPoliticos;

public class Main {
    public static void main(String[] args) {
        ListaPoliticos lista = new ListaPoliticos();
        PoliticoController controller = new PoliticoController(lista);

        // Datos iniciales: 10 000 políticos (el usuario puede regenerar desde la UI)
        controller.generarPoliticos(10_000, 42L);

        javax.swing.SwingUtilities.invokeLater(() -> {
            VistaPoliticos vista = new VistaPoliticos();
            vista.setController(controller);
            vista.setVisible(true);
        });
    }
}
