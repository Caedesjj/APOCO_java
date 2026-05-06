package view;

import controller.PoliticoController;
import model.MatrizAuditorio;
import model.Politico;
import model.ResultadoOrdenamiento;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class VistaPoliticos extends JFrame {

    // ── Componentes principales ──────────────────────────────────────────────
    private JTextArea        areaResultados;
    private JLabel           lblCantidad;
    private JLabel           lblEstado;
    private PoliticoController controller;

    // Panel lateral de controles
    private JComboBox<String> cbAlgoritmo;
    private JComboBox<String> cbCriterio;
    private JButton           btnOrdenar;
    private JButton           btnMostrarLista;
    private JButton           btnAuditorio;
    private JTextField        txtFilas;
    private JTextField        txtColumnas;
    private JTextField        txtCantidadPoliticos;
    private JTextArea         areaMetricas;

    // ── Constructor ──────────────────────────────────────────────────────────

    public VistaPoliticos() {
        setTitle("APOCO – Asociación de Políticos Corruptos");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

        construirHeader();
        construirPanelCentral();
        construirPanelLateral();
        construirFooter();
    }

    // ── Construcción de la UI ─────────────────────────────────────────────────

    private void construirHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(30, 30, 60));
        header.setBorder(new EmptyBorder(10, 15, 10, 15));

        JLabel titulo = new JLabel("🏛  SISTEMA DE GESTIÓN APOCO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        header.add(titulo, BorderLayout.CENTER);

        lblCantidad = new JLabel("Políticos registrados: 0", SwingConstants.RIGHT);
        lblCantidad.setFont(new Font("Arial", Font.PLAIN, 13));
        lblCantidad.setForeground(new Color(180, 220, 255));
        header.add(lblCantidad, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);
    }

    private void construirPanelCentral() {
        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaResultados.setBackground(new Color(245, 245, 250));
        areaResultados.setBorder(new EmptyBorder(5, 8, 5, 8));
        areaResultados.setText("  Usa los controles de la derecha para ordenar la lista\n"
                             + "  y construir el auditorio.");

        JScrollPane scroll = new JScrollPane(areaResultados);
        scroll.setBorder(new TitledBorder("Resultados"));
        add(scroll, BorderLayout.CENTER);
    }

    private void construirPanelLateral() {
        JPanel lateral = new JPanel();
        lateral.setLayout(new BoxLayout(lateral, BoxLayout.Y_AXIS));
        lateral.setPreferredSize(new Dimension(280, 0));
        lateral.setBorder(new EmptyBorder(10, 8, 10, 8));
        lateral.setBackground(new Color(240, 240, 248));

        // ── Sección: Generación de datos ──
        lateral.add(seccion("🎲  Generar políticos"));
        lateral.add(Box.createVerticalStrut(4));

        // Botones de acceso rápido
        JPanel panelRapido = new JPanel(new GridLayout(1, 4, 4, 0));
        panelRapido.setOpaque(false);
        panelRapido.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        for (String cantidad : new String[]{"1K", "10K", "100K", "1M"}) {
            JButton b = new JButton(cantidad);
            b.setFont(new Font("Arial", Font.BOLD, 11));
            b.setBackground(new Color(80, 120, 200));
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.addActionListener(e -> generarConAccesoRapido(cantidad));
            panelRapido.add(b);
        }
        lateral.add(panelRapido);
        lateral.add(Box.createVerticalStrut(5));

        // Campo personalizado
        JPanel panelCustom = new JPanel(new BorderLayout(4, 0));
        panelCustom.setOpaque(false);
        panelCustom.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtCantidadPoliticos = new JTextField("10000");
        panelCustom.add(etiqueta("Cantidad:"), BorderLayout.WEST);
        panelCustom.add(txtCantidadPoliticos, BorderLayout.CENTER);
        JButton btnGenerar = new JButton("Generar");
        btnGenerar.setFont(new Font("Arial", Font.BOLD, 11));
        btnGenerar.setBackground(new Color(180, 100, 30));
        btnGenerar.setForeground(Color.WHITE);
        btnGenerar.setFocusPainted(false);
        btnGenerar.addActionListener(e -> generarPersonalizado());
        panelCustom.add(btnGenerar, BorderLayout.EAST);
        lateral.add(panelCustom);
        lateral.add(Box.createVerticalStrut(12));

        // ── Sección: Mostrar lista ──
        lateral.add(seccion("📋  Lista de políticos"));
        btnMostrarLista = boton("Mostrar lista completa", new Color(60, 100, 180));
        btnMostrarLista.addActionListener(e -> mostrarLista());
        lateral.add(btnMostrarLista);
        lateral.add(Box.createVerticalStrut(12));

        // ── Sección: Ordenamiento ──
        lateral.add(seccion("⚙  Ordenamiento"));

        lateral.add(etiqueta("Algoritmo:"));
        cbAlgoritmo = new JComboBox<>(new String[]{
            "BubbleSort", "InsertionSort", "QuickSort", "MergeSort"
        });
        cbAlgoritmo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        lateral.add(cbAlgoritmo);
        lateral.add(Box.createVerticalStrut(4));

        lateral.add(etiqueta("Criterio:"));
        cbCriterio = new JComboBox<>(new String[]{
            "Dinero (mayor → menor)",
            "Dinero (menor → mayor)",
            "Edad   (menor → mayor)",
            "Edad   (mayor → menor)"
        });
        cbCriterio.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        lateral.add(cbCriterio);
        lateral.add(Box.createVerticalStrut(6));

        btnOrdenar = boton("Ordenar y mostrar", new Color(40, 140, 80));
        btnOrdenar.addActionListener(e -> ordenarYMostrar());
        lateral.add(btnOrdenar);
        lateral.add(Box.createVerticalStrut(12));

        // ── Sección: Auditorio ──
        lateral.add(seccion("🎭  Auditorio"));

        JPanel panelKM = new JPanel(new GridLayout(2, 2, 4, 4));
        panelKM.setOpaque(false);
        panelKM.setMaximumSize(new Dimension(Integer.MAX_VALUE, 56));
        panelKM.add(etiqueta("Filas (k):"));
        txtFilas = new JTextField("5");
        panelKM.add(txtFilas);
        panelKM.add(etiqueta("Columnas (m):"));
        txtColumnas = new JTextField("4");
        panelKM.add(txtColumnas);
        lateral.add(panelKM);
        lateral.add(Box.createVerticalStrut(4));

        btnAuditorio = boton("Construir auditorio", new Color(140, 60, 160));
        btnAuditorio.addActionListener(e -> construirAuditorio());
        lateral.add(btnAuditorio);
        lateral.add(Box.createVerticalStrut(12));

        // ── Métricas ──
        lateral.add(seccion("📊  Métricas del último ordenamiento"));
        areaMetricas = new JTextArea(5, 1);
        areaMetricas.setEditable(false);
        areaMetricas.setFont(new Font("Monospaced", Font.PLAIN, 11));
        areaMetricas.setBackground(new Color(230, 235, 245));
        areaMetricas.setBorder(new EmptyBorder(4, 6, 4, 6));
        areaMetricas.setText("(sin datos aún)");
        JScrollPane scrollMetricas = new JScrollPane(areaMetricas);
        scrollMetricas.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        lateral.add(scrollMetricas);

        add(lateral, BorderLayout.EAST);
    }

    private void construirFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(50, 50, 80));
        footer.setBorder(new EmptyBorder(4, 10, 4, 10));

        lblEstado = new JLabel("Listo.");
        lblEstado.setForeground(Color.LIGHT_GRAY);
        lblEstado.setFont(new Font("Arial", Font.ITALIC, 12));
        footer.add(lblEstado, BorderLayout.WEST);

        add(footer, BorderLayout.SOUTH);
    }

    // ── Helpers de UI ────────────────────────────────────────────────────────

    private JLabel seccion(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Arial", Font.BOLD, 13));
        l.setForeground(new Color(30, 30, 80));
        l.setBorder(new MatteBorder(0, 0, 1, 0, new Color(180, 180, 210)));
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JLabel etiqueta(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Arial", Font.PLAIN, 12));
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JButton boton(String texto, Color color) {
        JButton b = new JButton(texto);
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Arial", Font.BOLD, 12));
        b.setFocusPainted(false);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        return b;
    }

    // ── Lógica de la vista ────────────────────────────────────────────────────

    public void setController(PoliticoController ctrl) {
        this.controller = ctrl;
        if (ctrl != null) {
            actualizarCantidad();
            lblEstado.setText("Datos cargados. Elige un algoritmo para ordenar.");
        }
    }

    private void actualizarCantidad() {
        int n = controller.obtenerCantidadPoliticos();
        lblCantidad.setText("Políticos registrados: " + String.format("%,d", n));
    }

    private void generarConAccesoRapido(String etiqueta) {
        int n;
        switch (etiqueta) {
            case "1K":   n = 1_000;       break;
            case "10K":  n = 10_000;      break;
            case "100K": n = 100_000;     break;
            case "1M":   n = 1_000_000;   break;
            default:     return;
        }
        txtCantidadPoliticos.setText(String.valueOf(n));
        ejecutarGeneracion(n);
    }

    private void generarPersonalizado() {
        int n;
        try {
            n = Integer.parseInt(txtCantidadPoliticos.getText().trim().replace("_", "").replace(",", "").replace(".", ""));
            if (n <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Ingresa un número entero positivo de políticos.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ejecutarGeneracion(n);
    }

    private void ejecutarGeneracion(int n) {
        if (sinControlador()) return;
        setButtonsEnabled(false);
        lblEstado.setText(String.format("Generando %,d políticos…", n));
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        SwingWorker<Void, Void> w = new SwingWorker<>() {
            protected Void doInBackground() {
                controller.regenerarPoliticos(n);
                return null;
            }
            protected void done() {
                try {
                    get();
                    actualizarCantidad();
                    areaResultados.setText(String.format(
                        "  ✔  Se generaron %,d políticos correctamente.\n\n"
                      + "  Ahora puedes:\n"
                      + "   • Ordenarlos con el algoritmo que elijas\n"
                      + "   • Construir el auditorio\n"
                      + "   • Mostrar la lista completa", n));
                    areaMetricas.setText("(sin datos aún)");
                    lblEstado.setText(String.format("Listo: %,d políticos generados.", n));
                } catch (Exception ex) {
                    error(ex);
                } finally {
                    setCursor(Cursor.getDefaultCursor());
                    setButtonsEnabled(true);
                }
            }
        };
        w.execute();
    }

    /** Habilita o deshabilita todos los botones de acción mientras se procesa. */
    private void setButtonsEnabled(boolean enabled) {
        btnMostrarLista.setEnabled(enabled);
        btnOrdenar.setEnabled(enabled);
        btnAuditorio.setEnabled(enabled);
    }

    private void mostrarLista() {
        if (sinControlador()) return;
        lblEstado.setText("Cargando lista…");
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        SwingWorker<String, Void> w = new SwingWorker<>() {
            protected String doInBackground() {
                return controller.obtenerListaComoTexto();
            }
            protected void done() {
                try {
                    areaResultados.setText(get());
                    areaResultados.setCaretPosition(0);
                    lblEstado.setText("Lista mostrada.");
                } catch (Exception ex) {
                    error(ex);
                } finally {
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        };
        w.execute();
    }

    private void ordenarYMostrar() {
        if (sinControlador()) return;

        String alg = algoritmoSeleccionado();
        int crit   = cbCriterio.getSelectedIndex();

        lblEstado.setText("Ordenando con " + alg + "…");
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        SwingWorker<ResultadoOrdenamiento, Void> w = new SwingWorker<>() {
            protected ResultadoOrdenamiento doInBackground() {
                return controller.ordenar(alg, comparadorSeleccionado(crit));
            }
            protected void done() {
                try {
                    ResultadoOrdenamiento res = get();
                    mostrarMetricas(res);
                    areaResultados.setText(controller.obtenerListaComoTexto());
                    areaResultados.setCaretPosition(0);
                    lblEstado.setText("Ordenamiento completado: " + res.getAlgoritmo());
                } catch (Exception ex) {
                    error(ex);
                } finally {
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        };
        w.execute();
    }

    private void construirAuditorio() {
        if (sinControlador()) return;

        int k, m;
        try {
            k = Integer.parseInt(txtFilas.getText().trim());
            m = Integer.parseInt(txtColumnas.getText().trim());
            if (k <= 0 || m <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Ingresa valores enteros positivos para filas y columnas.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String alg = algoritmoSeleccionado();
        lblEstado.setText("Construyendo auditorio con " + alg + "…");
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        SwingWorker<String, Void> w = new SwingWorker<>() {
            ResultadoOrdenamiento res;
            protected String doInBackground() {
                res = controller.construirAuditorio(k, m, alg);
                MatrizAuditorio aud = controller.getAuditorio();

                StringBuilder sb = new StringBuilder(aud.toTexto());
                sb.append("\n── Consultas del auditorio ──────────────────────────\n");

                Politico pj = aud.masJoven();
                Politico pv = aud.masViejo();
                Politico pr = aud.masRico();
                Politico pm = aud.menosRico();

                sb.append("  Más joven  : ").append(pj != null ? pj : "N/A").append('\n');
                sb.append("  Más viejo  : ").append(pv != null ? pv : "N/A").append('\n');
                sb.append("  Más rico   : ").append(pr != null ? pr : "N/A").append('\n');
                sb.append("  Menos rico : ").append(pm != null ? pm : "N/A").append('\n');
                return sb.toString();
            }
            protected void done() {
                try {
                    areaResultados.setText(get());
                    areaResultados.setCaretPosition(0);
                    mostrarMetricas(res);
                    lblEstado.setText("Auditorio construido (" + k + "×" + m + ").");
                } catch (Exception ex) {
                    error(ex);
                } finally {
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        };
        w.execute();
    }

    // ── Utilidades ───────────────────────────────────────────────────────────

    private String algoritmoSeleccionado() {
        switch (cbAlgoritmo.getSelectedIndex()) {
            case 0: return "bubble";
            case 1: return "insertion";
            case 2: return "quick";
            case 3: return "merge";
            default: return "quick";
        }
    }

    private java.util.Comparator<model.Politico> comparadorSeleccionado(int idx) {
        switch (idx) {
            case 0: return PoliticoController.POR_DINERO_DESC;
            case 1: return PoliticoController.POR_DINERO_ASC;
            case 2: return PoliticoController.POR_EDAD_ASC;
            case 3: return PoliticoController.POR_EDAD_DESC;
            default: return PoliticoController.POR_DINERO_DESC;
        }
    }

    private void mostrarMetricas(ResultadoOrdenamiento res) {
        areaMetricas.setText(res.toString());
    }

    private boolean sinControlador() {
        if (controller == null) {
            areaResultados.setText("Controlador no configurado.");
            return true;
        }
        return false;
    }

    private void error(Exception ex) {
        ex.printStackTrace();
        areaResultados.setText("Error: " + ex.getMessage());
        lblEstado.setText("Error.");
    }
}
