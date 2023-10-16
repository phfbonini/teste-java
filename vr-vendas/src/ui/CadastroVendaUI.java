package ui;

import model.Cliente;
import service.ClienteService;
import service.ProdutoService;
import service.VendaService;
import model.Produto;
import model.Venda;
import model.ItemVenda;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CadastroVendaUI {
    private VendaService vendaService;
    private ClienteService clienteService;
    private int vendaId = -1;
    private DefaultTableModel tableModel;
    private JTable table;
    private JComboBox<Cliente> clienteComboBox;

    private JFormattedTextField dataField;

    private JTextField valorTotalField; 

    private JTextField quantidadeField;

    private JComboBox<String> produtoComboBox;

    public CadastroVendaUI() {
        vendaService = new VendaService();
        clienteService = new ClienteService();


    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Cadastro de Venda");
        frame.setSize(1040, 720);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setBackground(new Color(240, 240, 240));

        placeComponents(panel);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Cadastro de Venda");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(375, 20, 250, 30);
        panel.add(titleLabel);

        JLabel dataLabel = new JLabel("Data:");
        dataLabel.setBounds(20, 70, 80, 25);
        panel.add(dataLabel);




        // Obtém a data atual
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataAtual = dateFormat.format(currentDate);

        // Define a data atual no campo de data
        dataField = new JFormattedTextField(dataAtual);
        dataField.setBounds(120, 70, 150, 25);
        panel.add(dataField);

//        try {
//            MaskFormatter mask = new MaskFormatter("##/##/####");
//            dataField = new JFormattedTextField(mask);
//            dataField.setBounds(120, 70, 150, 25);
//            panel.add(dataField);JToggleButton toggleDataAtualButton = new JToggleButton("Usar Data Atual");
//    toggleDataAtualButton.setBounds(280, 70, 150, 25);
//    panel.add(toggleDataAtualButton);
//
//    toggleDataAtualButton.addActionListener(new ActionListener() {
//        public void actionPerformed(ActionEvent e) {
//            useDataAtual = !useDataAtual;
//            if (useDataAtual) {
//                Date currentDate = new Date();
//                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//                String dataAtual = dateFormat.format(currentDate);
//                dataField.setText(dataAtual);
//                dataField.setEditable(false);
//            } else {
//                dataField.setText("");
//                dataField.setEditable(true);
//            }
//        }
//    });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        JLabel clienteLabel = new JLabel("Cliente:");
        clienteLabel.setBounds(20, 110, 80, 25);
        panel.add(clienteLabel);

        clienteComboBox = new JComboBox<>();
        clienteComboBox.setBounds(120, 110, 150, 25);
        panel.add(clienteComboBox);


        List<Cliente> clientes = clienteService.getAllClientes();
        for (Cliente cliente : clientes) {
            clienteComboBox.addItem(cliente);
        }

        JLabel valorTotalLabel = new JLabel("Valor Total:");
        valorTotalLabel.setBounds(20, 150, 80, 25);
        panel.add(valorTotalLabel);

        valorTotalField = new JTextField();
        valorTotalField.setBounds(120, 150, 150, 25);
        valorTotalField.setEditable(false);
        panel.add(valorTotalField);

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        tableModel.addColumn("ID");
        tableModel.addColumn("Descrição");
        tableModel.addColumn("Preço");
        tableModel.addColumn("Quantidade");
        tableModel.addColumn("Preço Total");

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 200, 980, 200);
        panel.add(scrollPane);

        JLabel selectProdutoLabel = new JLabel("Selecionar Produto:");
        selectProdutoLabel.setBounds(20, 410, 150, 25);
        panel.add(selectProdutoLabel);

        produtoComboBox = new JComboBox<>();
        produtoComboBox.setBounds(150, 410, 260, 30);

        panel.add(produtoComboBox);
        List<Produto> produtos = ProdutoService.getAllProdutos();

        List<String> produtosStrings = new ArrayList<>();
        for (Produto produto : produtos) {
            String produtoString = produto.getId() + " - " + produto.getDescricao() + " - R$ " + produto.getPreco();
            produtosStrings.add(produtoString);
        }

        for (String produtoString : produtosStrings) {
            produtoComboBox.addItem(produtoString);
        }


        JLabel quantidadeLabel = new JLabel("Quantidade:");
        quantidadeLabel.setBounds(20, 450, 100, 25);
        panel.add(quantidadeLabel);

        quantidadeField = new JTextField();
        quantidadeField.setBounds(150, 450, 50, 30);

        panel.add(quantidadeField);

        JButton addProdutoButton = new JButton("Adicionar Produto");
        addProdutoButton.setBounds(210, 450, 200, 30);
        addProdutoButton.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(addProdutoButton);

        JButton cadastrarVendaButton = new JButton("Cadastrar Venda");
        cadastrarVendaButton.setBounds(800, 410, 200, 70);
        cadastrarVendaButton.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(cadastrarVendaButton);

        clienteComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Cliente selectedCliente = (Cliente) clienteComboBox.getSelectedItem();
                }
            }
        });


        addProdutoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedProdutoString = (String) produtoComboBox.getSelectedItem();
                String quantidadeText = quantidadeField.getText();

                if (quantidadeText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Insira a quantidade do produto.", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        int quantidade = Integer.parseInt(quantidadeText);

                        if (quantidade == 0) {
                            JOptionPane.showMessageDialog(null, "A quantidade deve ser maior que zero.", "Erro", JOptionPane.ERROR_MESSAGE);
                        } else {
                            adicionarProduto(selectedProdutoString, quantidade);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "A quantidade inserida não é um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

        });



        cadastrarVendaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String dataText = dataField.getText();
                Date data = null;

                try {
                    SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    data = inputDateFormat.parse(dataText);

                    SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = dbDateFormat.format(data);

                    System.out.println("Data formatada para o banco de dados: " + formattedDate);

                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Data inválida. Use o formato dd/MM/yyyy.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Cliente selectedCliente = (Cliente) clienteComboBox.getSelectedItem();

                if (selectedCliente == null) {
                    JOptionPane.showMessageDialog(null, "Selecione um cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String valorTotalStr;
                double valorTotal;
                try{
                     valorTotalStr = valorTotalField.getText().replace(",", ".");
                    valorTotal = Double.parseDouble(valorTotalStr);
                } catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Adicione ao menos um produto para cadastrar a venda", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                Venda venda = new Venda(-1, data, selectedCliente.getId(), valorTotal, "efetivada");


                List<ItemVenda> itensVenda = new ArrayList<>();

                for (int row = 0; row < tableModel.getRowCount(); row++) {
                    int produtoId = (int) tableModel.getValueAt(row, 0);
                    int quantidade = (int) tableModel.getValueAt(row, 3);
                    double preco = (double) tableModel.getValueAt(row, 2);
                    double valorTotalItem = (double) tableModel.getValueAt(row, 4);

                    ItemVenda item = new ItemVenda(-1, produtoId, quantidade, preco, valorTotalItem);
                    itensVenda.add(item);
                }

                try {
                    vendaService.cadastrarVenda(venda, itensVenda);
                    JOptionPane.showMessageDialog(null, "Venda Efetivada com Sucesso!", "Venda Cadastrada", JOptionPane.INFORMATION_MESSAGE);

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                limparInterface();
            }
        });


    }
    private void adicionarProduto(String selectedProdutoString, int quantidade) {
        int produtoId = Integer.parseInt(selectedProdutoString.split(" - ")[0]);


        Produto produto = ProdutoService.getProdutoById(produtoId);

        if (produto == null) {
            JOptionPane.showMessageDialog(null, "Produto não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int rowToUpdate = -1;
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            int idNaTabela = (int) tableModel.getValueAt(row, 0);
            if (idNaTabela == produtoId) {
                rowToUpdate = row;
                break;
            }
        }

        if (rowToUpdate != -1) {
            int quantidadeAtual = (int) tableModel.getValueAt(rowToUpdate, 3);

            int quantidadeTotal = quantidade + quantidadeAtual;
            if (quantidadeTotal > produto.getQuantidade()) {
                JOptionPane.showMessageDialog(null, "Quantidade indisponível para o produto.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            quantidadeAtual += quantidade;
            tableModel.setValueAt(quantidadeAtual, rowToUpdate, 3);

            double preco = produto.getPreco();
            double valorTotal = preco * quantidadeAtual;
            tableModel.setValueAt(valorTotal, rowToUpdate, 4);
        } else {
            if (quantidade > produto.getQuantidade()) {
                JOptionPane.showMessageDialog(null, "Quantidade indisponível para o produto.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double preco = produto.getPreco();
            double valorTotal = preco * quantidade;

            Object[] rowData = {produto.getId(), produto.getDescricao(), preco, quantidade, valorTotal};
            tableModel.addRow(rowData);
        }

        updateValorTotal();
    }

    private void updateValorTotal() {
        double valorTotal = 0.0;

        for (int row = 0; row < tableModel.getRowCount(); row++) {
            double itemTotal = (double) tableModel.getValueAt(row, 4);
            valorTotal += itemTotal;
        }

        valorTotalField.setText(String.format("%.2f", valorTotal));
    }

    private void limparInterface() {
        dataField.setText("");

        clienteComboBox.setSelectedIndex(0);

        valorTotalField.setText("");

        tableModel.setRowCount(0);

        quantidadeField.setText("");
        produtoComboBox.setSelectedIndex(0);
    }

//    private boolean isDateValid(String dateText) {
//        try {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//            dateFormat.setLenient(false);
//
//            Date date = dateFormat.parse(dateText);
//
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(date);
//
//            int year = cal.get(Calendar.YEAR);
//            int month = cal.get(Calendar.MONTH) + 1; // Mês começa em 0
//            int day = cal.get(Calendar.DAY_OF_MONTH);
//
//            // Verifica se o ano está dentro de um intervalo razoável (por exemplo, de 1900 a 2100)
//            if (year < 1900 || year > 2100) {
//                JOptionPane.showMessageDialog(null, "O ano deve estar entre 1900 e 2100.", "Erro", JOptionPane.ERROR_MESSAGE);
//                return false;
//            }
//
//            // Verifica se o mês e o dia estão dentro dos limites válidos
//            if (month < 1 || month > 12 || day < 1 || day > 31) {
//                JOptionPane.showMessageDialog(null, "Data inválida. Use o formato dd/MM/yyyy.", "Erro", JOptionPane.ERROR_MESSAGE);
//                return false;
//            }
//
//            // Verificação de datas específicas inválidas, como 30/02/2023
//            if (day > 28 && month == 2 && !isLeapYear(year)) {
//                JOptionPane.showMessageDialog(null, "Data inválida. Fevereiro não pode ter mais de 28 dias neste ano.", "Erro", JOptionPane.ERROR_MESSAGE);
//                return false;
//            }
//
//            return true;
//        } catch (ParseException e) {
//            JOptionPane.showMessageDialog(null, "Data inválida. Use o formato dd/MM/yyyy.", "Erro", JOptionPane.ERROR_MESSAGE);
//            return false;
//        }
//    }
//
//    // Função para verificar se o ano é bissexto
//    private boolean isLeapYear(int year) {
//        return (year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0));
//    }

    




    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CadastroVendaUI cadastroVendaUI = new CadastroVendaUI();
            cadastroVendaUI.createAndShowGUI();
        });
    }
}
