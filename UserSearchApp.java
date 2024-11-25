import java.util.ArrayList;
import java.util.List;

// Importações necessárias para manipulação de elementos de interface gráfica (GUI)
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserSearchApp extends JFrame {
    private JPanel contentPanel;
    private JTextField inputSearch;
    private JButton btnLimpar;

    private List<User> items;

    public UserSearchApp() {
        super("User Search App");

        // Inicialização da lista de itens
        items = new ArrayList<>();

        contentPanel = new JPanel(new GridLayout(0, 1));
        inputSearch = new JTextField();
        btnLimpar = new JButton("Refresh");

        // Adicionando os componentes à janela
        add(contentPanel, BorderLayout.CENTER);
        add(inputSearch, BorderLayout.NORTH);
        add(btnLimpar, BorderLayout.SOUTH);

        // Campo de busca
        inputSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterItems();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterItems();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterItems();
            }
        });

        // Ação do botão de limpar
        btnLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });

        // Chamada ao método para buscar e exibir os usuários
        fetchUsers();
    }

    // Método para filtrar os itens de acordo com a entrada de busca
    private void filterItems() {
        String searchText = inputSearch.getText().toLowerCase();
        contentPanel.removeAll();
        for (User user : items) {
            if (userContainsSearchText(user, searchText)) {
                printUserInfo(user);
            }
        }
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Método para verificar se algum campo do usuário contém o texto de busca
    private boolean userContainsSearchText(User user, String searchText) {
        return user.getName().toLowerCase().contains(searchText) ||
                user.getUsername().toLowerCase().contains(searchText) ||
                user.getEmail().toLowerCase().contains(searchText) ||
                user.getPhone().toLowerCase().contains(searchText) ||
                user.getWebsite().toLowerCase().contains(searchText) ||
                user.getCompany().getName().toLowerCase().contains(searchText) ||
                user.getCompany().getCatchPhrase().toLowerCase().contains(searchText) ||
                user.getCompany().getBs().toLowerCase().contains(searchText) ||
                user.getAddress().getStreet().toLowerCase().contains(searchText) ||
                user.getAddress().getSuite().toLowerCase().contains(searchText) ||
                user.getAddress().getCity().toLowerCase().contains(searchText) ||
                user.getAddress().getZipcode().toLowerCase().contains(searchText) ||
                user.getAddress().getGeo().getLat().toLowerCase().contains(searchText) ||
                user.getAddress().getGeo().getLng().toLowerCase().contains(searchText);
    }

    // Método para imprimir as informações do usuário na GUI
    private void printUserInfo(User user) {
        JPanel userPanel = new JPanel(new GridLayout(0, 1));
        userPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        userPanel.add(new JLabel("Name: " + user.getName()));
        userPanel.add(new JLabel("Username: " + user.getUsername()));
        userPanel.add(new JLabel("Email: " + user.getEmail()));
        userPanel.add(new JLabel("Contato: " + user.getPhone()));
        userPanel.add(new JLabel("Website: " + user.getWebsite()));
        userPanel.add(new JLabel("Razão social: " + user.getCompany().getName()));
        userPanel.add(new JLabel("Slogan da empresa: " + user.getCompany().getCatchPhrase()));
        userPanel.add(new JLabel("Valores: " + user.getCompany().getBs()));
        userPanel.add(new JLabel("Street: " + user.getAddress().getStreet() + ", " +
                user.getAddress().getSuite() + ", " + user.getAddress().getCity()));
        userPanel.add(new JLabel("CEP: " + user.getAddress().getZipcode()));
        userPanel.add(new JLabel("Geolocalização: Latitude = " + user.getAddress().getGeo().getLat() +
                " Longitude = " + user.getAddress().getGeo().getLng()));

        contentPanel.add(userPanel);
    }

    // Método para buscar os usuários da API e exibi-los na GUI
    private void fetchUsers() {
        // Simulação de dados, já que não podemos acessar APIs em Java Swing diretamente
        // Aqui, preenchemos a lista de items com alguns usuários fictícios
        items.add(new User("John Doe", "johndoe", "john@example.com", "123-456-7890", "example.com",
                new Company("Example Inc", "Catch phrase", "bs"), new Address("123 Main St", "Suite 101",
                "Springfield", "12345", new Geo("0", "0"))));
        items.add(new User("Jane Smith", "janesmith", "jane@example.com", "987-654-3210", "example.com",
                new Company("Example Corp", "Catch phrase", "bs"), new Address("456 Oak St", "Apt 202",
                "Springfield", "54321", new Geo("0", "0"))));
        items.add(new User("Alice Johnson", "alicej", "alice@example.com", "111-222-3333", "example.com",
                new Company("Tech Solutions", "Innovate daily", "tech"), new Address("789 Pine St", "Floor 3",
                "Metropolis", "67890", new Geo("10", "20"))));
        items.add(new User("Bob Brown", "bobb", "bob@example.com", "444-555-6666", "example.net",
                new Company("Innovate LLC", "Think big", "solutions"), new Address("321 Maple Ave", "Unit 4",
                "Smallville", "11223", new Geo("30", "40"))));
        items.add(new User("Carol White", "carolw", "carol@example.org", "777-888-9999", "example.org",
                new Company("Future Inc", "Building tomorrow", "growth"), new Address("654 Elm St", "Suite 5B",
                "Gotham", "98765", new Geo("50", "60"))));
        items.add(new User("David Wilson", "davidw", "david@example.com", "101-202-3030", "example.biz",
                new Company("NextGen Co", "Leading the future", "nextgen"), new Address("987 Cedar Blvd", "Building A",
                "Riverdale", "22445", new Geo("70", "80"))));

        // Exibindo os usuários na GUI
        for (User user : items) {
            printUserInfo(user);
        }
    }

    // Método para atualizar a GUI
    private void refresh() {
        // Recarrega a janela
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dispose();
                new UserSearchApp().setVisible(true);
            }
        });
    }

    // Classe para representar os dados do usuário
    static class User {
        private String name;
        private String username;
        private String email;
        private String phone;
        private String website;
        private Company company;
        private Address address;

        public User(String name, String username, String email, String phone, String website, Company company, Address address) {
            this.name = name;
            this.username = username;
            this.email = email;
            this.phone = phone;
            this.website = website;
            this.company = company;
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public String getWebsite() {
            return website;
        }

        public Company getCompany() {
            return company;
        }

        public Address getAddress() {
            return address;
        }
    }

    // Classe para representar os dados da empresa
    static class Company {
        private String name;
        private String catchPhrase;
        private String bs;

        public Company(String name, String catchPhrase, String bs) {
            this.name = name;
            this.catchPhrase = catchPhrase;
            this.bs = bs;
        }

        public String getName() {
            return name;
        }

        public String getCatchPhrase() {
            return catchPhrase;
        }

        public String getBs() {
            return bs;
        }
    }

    // Classe para representar o endereço do usuário
    static class Address {
        private String street;
        private String suite;
        private String city;
        private String zipcode;
        private Geo geo;

        public Address(String street, String suite, String city, String zipcode, Geo geo) {
            this.street = street;
            this.suite = suite;
            this.city = city;
            this.zipcode = zipcode;
            this.geo = geo;
        }

        public String getStreet() {
            return street;
        }

        public String getSuite() {
            return suite;
        }

        public String getCity() {
            return city;
        }

        public String getZipcode() {
            return zipcode;
        }

        public Geo getGeo() {
            return geo;
        }
    }

    // Classe para representar os dados de geolocalização
    static class Geo {
        private String lat;
        private String lng;

        public Geo(String lat, String lng) {
            this.lat = lat;
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public String getLng() {
            return lng;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new UserSearchApp();
                frame.setSize(800, 600);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
