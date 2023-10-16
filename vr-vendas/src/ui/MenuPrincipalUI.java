package ui;

import com.sun.tools.javac.Main;
import service.DatabaseService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipalUI {
    public static void main(String[] args) {
        DatabaseService.connectToDatabase();


        JFrame frame = new JFrame("Sistema de Gestão de Vendas");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1020, 720);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(null);
        panel.setBackground(new Color(240, 240, 240));

        JLabel titleLabel = new JLabel("Sistema de Gestão VR-Software");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(327, 50, 370, 30);
        panel.add(titleLabel);

        JLabel subtitleLabel = new JLabel("Bem-vindo");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitleLabel.setBounds(460, 90, 90, 20);
        panel.add(subtitleLabel);

        JButton cadastrarVendaButton = new JButton("Cadastrar Venda");
        cadastrarVendaButton.setFont(new Font("Arial", Font.BOLD, 18));
        cadastrarVendaButton.setBounds(50, 150, 920, 50);
        panel.add(cadastrarVendaButton);

        cadastrarVendaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CadastroVendaUI cadastroVendaUI = new CadastroVendaUI();
                cadastroVendaUI.createAndShowGUI();
            }
        });

        JButton consultarVendasButton = new JButton("Consultar Vendas");
        JButton cadastrarProdutoButton = new JButton("Cadastrar Produto");
        JButton cadastrarClienteButton = new JButton("Cadastrar Cliente");

        int buttonWidth = 294;
        int buttonHeight = 50;
        int buttonSpacing = 20;
        int buttonX = 50;
        int buttonY = 220;

        consultarVendasButton.setFont(new Font("Arial", Font.BOLD, 18));
        cadastrarProdutoButton.setFont(new Font("Arial", Font.BOLD, 18));
        cadastrarClienteButton.setFont(new Font("Arial", Font.BOLD, 18));

        consultarVendasButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
        cadastrarProdutoButton.setBounds(buttonX + buttonWidth + buttonSpacing, buttonY, buttonWidth, buttonHeight);
        cadastrarClienteButton.setBounds(buttonX + 2 * (buttonWidth + buttonSpacing), buttonY, buttonWidth, buttonHeight);

        panel.add(consultarVendasButton);
        panel.add(cadastrarProdutoButton);
        panel.add(cadastrarClienteButton);

        consultarVendasButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConsultarVendasUI.main(null);
            }
        });

        cadastrarProdutoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CadastroProdutoUI.main(null);
            }
        });

        cadastrarClienteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CadastroClienteUI.main(null);
            }
        });

        JLabel footerLabel = new JLabel("Desenvolvido por Pedro Bonini :)");
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        footerLabel.setBounds(800, 650, 200, 20);
        panel.add(footerLabel);
    }
}