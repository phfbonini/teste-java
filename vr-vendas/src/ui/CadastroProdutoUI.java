package ui;

import service.ProdutoService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroProdutoUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Cadastro de Produto");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(450, 200);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel descricaoLabel = new JLabel("Descrição:");
        descricaoLabel.setBounds(10, 10, 80, 25);
        panel.add(descricaoLabel);

        JTextField descricaoText = new JTextField(165);
        descricaoText.setBounds(100, 10, 300, 25);
        panel.add(descricaoText);

        JLabel precoLabel = new JLabel("Preço:");
        precoLabel.setBounds(10, 40, 80, 25);
        panel.add(precoLabel);

        JTextField precoText = new JTextField(165);
        precoText.setBounds(100, 40, 300, 25);
        panel.add(precoText);

        JLabel quantidadeLabel = new JLabel("Quantidade:");
        quantidadeLabel.setBounds(10, 70, 80, 25);
        panel.add(quantidadeLabel);

        JTextField quantidadeText = new JTextField(165);
        quantidadeText.setBounds(100, 70, 300, 25);
        panel.add(quantidadeText);

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setBounds(100, 110, 300, 25);
        panel.add(cadastrarButton);

        cadastrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String descricao = descricaoText.getText();
                String precoStr = precoText.getText();
                String quantidadeStr = quantidadeText.getText();

                if (!descricao.isEmpty() && !precoStr.isEmpty() && !quantidadeStr.isEmpty()) {
                    try {
                        double preco = Double.parseDouble(precoStr);
                        int quantidade = Integer.parseInt(quantidadeStr);
                        ProdutoService.cadastrarProduto(descricao, preco, quantidade);
                        JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
                        descricaoText.setText("");
                        precoText.setText("");
                        quantidadeText.setText("");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Preço e quantidade devem ser números válidos.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
                }
            }
        });
    }
}
