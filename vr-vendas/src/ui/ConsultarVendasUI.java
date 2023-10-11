package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Venda;
import model.ItemVenda;
import service.VendaService;
import service.ClienteService;

public class ConsultarVendasUI {
    private JFrame frame;
    private JPanel detailedPanel;
    private VendaService vendaService;
    private JButton editarButton;
    private JButton estornarVendaButton;
    private List<JToggleButton> toggleButtons;

    private Venda vendaSelecionada;

    public ConsultarVendasUI() {
        frame = new JFrame("Lista de Vendas");
        detailedPanel = new JPanel(new BorderLayout());
        vendaService = new VendaService();
        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(detailedPanel), BorderLayout.CENTER);

        toggleButtons = new ArrayList<>();

        JPanel buttonPanel = new JPanel();

        editarButton = new JButton("Editar");

        editarButton.setEnabled(false);

        buttonPanel.add(editarButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        estornarVendaButton = new JButton("Estornar Venda");
        estornarVendaButton.setEnabled(false);
        buttonPanel.add(estornarVendaButton);



        estornarVendaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (vendaSelecionada != null) {
                    String status = vendaSelecionada.getStatus();

                    if (status.equals("estornada")) {
                        JOptionPane.showMessageDialog(frame, "Essa venda já foi estornada.", "Venda Já Estornada", JOptionPane.ERROR_MESSAGE);
                    } else {
                        int confirmacao = JOptionPane.showConfirmDialog(frame, "Tem certeza de que deseja estornar esta venda?", "Confirmar Estorno", JOptionPane.YES_NO_OPTION);
                        if (confirmacao == JOptionPane.YES_OPTION) {
                            vendaSelecionada.setStatus("estornada");
                            System.out.println("Venda ID " + vendaSelecionada.getId() + " foi estornada.");
                        }
                    }
                }
            }
        });



        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (vendaSelecionada != null) {
                    System.out.println("Editando venda ID: " + vendaSelecionada.getId());
                }
            }
        });


        loadAccordionDetailedView();


        frame.setSize(1020, 720);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void loadAccordionDetailedView() {
        JPanel accordionPanel = new JPanel();
        accordionPanel.setLayout(new BoxLayout(accordionPanel, BoxLayout.Y_AXIS));
        accordionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        ActionListener toggleAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JToggleButton toggleButton = (JToggleButton) e.getSource();
                JPanel contentPanel = (JPanel) toggleButton.getClientProperty("content");


                for (JToggleButton otherToggleButton : toggleButtons) {
                    if (otherToggleButton != toggleButton) {
                        otherToggleButton.setSelected(false);
                        JPanel otherContentPanel = (JPanel) otherToggleButton.getClientProperty("content");
                        otherContentPanel.setVisible(false);
                    }
                }

                contentPanel.setVisible(toggleButton.isSelected());


                if (toggleButton.isSelected()) {

                    String vendaInfo = toggleButton.getText();
                    int vendaId = extrairIDVenda(vendaInfo);
                    vendaSelecionada = vendaService.getVendaById(vendaId);
                    System.out.println(vendaSelecionada);


                    estornarVendaButton.setEnabled(true);
                } else {

                    estornarVendaButton.setEnabled(false);
                }
            }
        };

        List<Venda> vendas = vendaService.getAllVendas();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for (Venda venda : vendas) {
            String clienteNome = ClienteService.getClienteById(venda.getClienteId()).getNome();
            String vendaInfo = "Venda ID: " + venda.getId() +
                    " - Data: " + dateFormat.format(venda.getData()) +
                    " - Cliente: " + clienteNome +
                    " - Valor Total: R$" + venda.getValorTotal() +
                    " - Status: " + venda.getStatus();

            JToggleButton toggleButton = new JToggleButton(vendaInfo);
            JPanel contentPanel = new JPanel(new BorderLayout());
            JTable detailedTable = createDetailedTable(venda);
            contentPanel.add(new JScrollPane(detailedTable), BorderLayout.CENTER);
            contentPanel.setVisible(false);
            toggleButton.putClientProperty("content", contentPanel);
            toggleButton.addActionListener(toggleAction);
            toggleButtons.add(toggleButton);

            accordionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            accordionPanel.add(toggleButton);
            accordionPanel.add(contentPanel);
        }

        detailedPanel.add(accordionPanel, BorderLayout.CENTER);


        JLabel instructionLabel = new JLabel("Clique sobre uma venda para visualizar mais detalhes");
        Font largerFont = instructionLabel.getFont().deriveFont(16.0f); 
        instructionLabel.setFont(largerFont);
        detailedPanel.add(instructionLabel, BorderLayout.NORTH);

    }

    public static int extrairIDVenda(String input) {
        Pattern pattern = Pattern.compile("Venda ID: (\\d+)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String idStr = matcher.group(1);
            return Integer.parseInt(idStr);
        }

        return -1;
    }

    private JTable createDetailedTable(Venda venda) {
        DefaultTableModel detailedTableModel = new DefaultTableModel(
                new String[]{"Produto ID", "Quantidade", "Preço", "Valor Total"}, 0);

        List<ItemVenda> itensVenda = vendaService.getItensVenda(venda.getId());
        for (ItemVenda item : itensVenda) {
            detailedTableModel.addRow(new Object[]{item.getProdutoId(), item.getQuantidade(), item.getPreco(), item.getValorTotal()});
        }

        return new JTable(detailedTableModel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConsultarVendasUI());
    }
}