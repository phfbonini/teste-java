package ui;

import service.ClienteService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroClienteUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Cadastro de Cliente");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setBounds(10, 10, 80, 25);
        panel.add(nomeLabel);

        JTextField nomeText = new JTextField(165);
        nomeText.setBounds(100, 10, 160, 25);
        panel.add(nomeText);

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setBounds(100, 50, 100, 25);
        panel.add(cadastrarButton);

        cadastrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = nomeText.getText();
                if (!nome.isEmpty()) {
                    ClienteService.cadastrarCliente(nome);
                    JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
                    nomeText.setText(""); // Limpa o campo após o cadastro
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, insira um nome válido.");
                }
            }
        });
    }
}
