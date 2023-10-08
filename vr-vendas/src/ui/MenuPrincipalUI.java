package ui;

import service.DatabaseService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipalUI {
    public static void main(String[] args) {
        DatabaseService.connectToDatabase(); // Inicializa o DatabaseService

        JFrame frame = new JFrame("Sistema de Gestão de Vendas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600); // Tamanho maior
        frame.setResizable(false);

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setBackground(new Color(240, 240, 240));

        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // Painel para rótulo de boas-vindas
        JPanel welcomePanel = new JPanel();
        welcomePanel.setBounds(50, 50, 900, 100);
        welcomePanel.setBackground(new Color(70, 130, 180));
        panel.add(welcomePanel);

        // Rótulo de boas-vindas
        JLabel welcomeLabel = new JLabel("Bem-vindo ao Sistema de Vendas");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        welcomePanel.add(welcomeLabel);

        // Botão "Cadastrar Cliente"
        JButton cadastrarClienteButton = new JButton("Cadastrar Cliente");
        cadastrarClienteButton.setBounds(400, 200, 200, 50);
        cadastrarClienteButton.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(cadastrarClienteButton);

        cadastrarClienteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CadastroClienteUI.main(null); // Inicia a UI de cadastro de cliente
            }
        });

        // Botão "Cadastrar Produto"
        JButton cadastrarProdutoButton = new JButton("Cadastrar Produto");
        cadastrarProdutoButton.setBounds(400, 300, 200, 50);
        cadastrarProdutoButton.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(cadastrarProdutoButton);

        cadastrarProdutoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CadastroProdutoUI.main(null); // Inicia a UI de cadastro de produto
            }
        });

        // Botão "Consultar Vendas"
        JButton consultarVendasButton = new JButton("Consultar Vendas");
        consultarVendasButton.setBounds(400, 400, 200, 50);
        consultarVendasButton.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(consultarVendasButton);

        consultarVendasButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
            }
        });
    }
}
