package view;

import business.CustomerController;
import business.ProductController;
import core.Helper;
import core.Item;
import entity.Customer;
import entity.Product;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DashboardUI extends JFrame {


    private JPanel container;
    private JLabel lbl_welcome;
    private JButton btn_logout;
    private JTabbedPane tab_menu;
    private JPanel pnl_customer;
    private JScrollPane scrl_customer;
    private JTable tbl_customer;
    private JPanel pnl_customer_filter;
    private JTextField fld_f_customername;
    private JComboBox<Customer.TYPE> cmb_f_customer_type;
    private JButton btn_customer_filter_reset;
    private JButton btn_customer_new;
    private JButton btn_customer_filter;
    private JLabel lbcustome_name;
    private JLabel lbl_f_customer_type;
    private JPanel pnl_product;
    private JScrollPane scrl_product;
    private JTable tbl_product;
    private JPanel pnl_product_filter;
    private JTextField fld_f_product_name;
    private JTextField fld_f_product_code;
    private JComboBox<Item> cmb_f_product_stock;
    private JButton btn_product_filter;
    private JButton btn_product_filter_reset;
    private JButton btn_product_new;
    private JLabel lbl_f__product_name;
    private JLabel lbl_f_product_code;
    private JLabel lbl_f_product_stock;
    private User user;
    private CustomerController customerController;
    private ProductController productController;
    private DefaultTableModel tmdl_customer = new DefaultTableModel();
    private DefaultTableModel tmdl_product = new DefaultTableModel();
    private JPopupMenu popup_customer = new JPopupMenu();
    private JPopupMenu popup_product = new JPopupMenu();

    public DashboardUI(User user) {
        this.user = user;
        this.customerController = new CustomerController();
        this.productController = new ProductController();
        if (user == null) {
            Helper.showMsg("error");
            dispose();
        }

        this.add(container);
        this.setTitle("Müşteri Yönetim Sisitemi");
        this.setSize(1000, 500);

        int x = ((Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2);
        int y = ((Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2);
        this.setLocation(x, y);
        this.setVisible(true);

        this.lbl_welcome.setText("Hoşgeldin: " + this.user.getName());


        this.btn_logout.addActionListener(e -> {
            dispose();
            LoginUI loginUI = new LoginUI();
        });

        // CUSTOMER TAB
        loadCustomerTable(null);
        loadCustomerPopupMenu();
        loadCustomerButtonEvent();
        this.cmb_f_customer_type.setModel(new DefaultComboBoxModel<>(Customer.TYPE.values()));
        this.cmb_f_customer_type.setSelectedItem(null);

        // PRODUCT TAB
        loadProductTable(null);
        loadProductPopupMenu();
        loadProductButtonEvent();
        this.cmb_f_product_stock.addItem(new Item(1, "Stokta Var"));
        this.cmb_f_product_stock.addItem(new Item(2, "Stokta Yok"));
        this.cmb_f_product_stock.setSelectedItem(null);

    }

    private void loadProductButtonEvent() {
        btn_product_new.addActionListener(e -> {
            ProductUI productUI = new ProductUI(new Product());
            productUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadProductTable(null);
                }
            });

        });

        this.btn_product_filter.addActionListener(e -> {
            ArrayList<Product> filteredProducts = this.productController.filter(
                    this.fld_f_product_name.getText(),
                    this.fld_f_product_code.getText(),
                    (Item) this.cmb_f_product_stock.getSelectedItem()
            );
            loadProductTable(filteredProducts);

        });

        this.btn_product_filter_reset.addActionListener(e -> {
            this.fld_f_product_code.setText(null);
            this.fld_f_product_name.setText(null);
            this.cmb_f_product_stock.setSelectedItem(null);
            loadProductTable(null);

        });

    }

    private void loadProductPopupMenu() {


        tbl_product.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectRow = tbl_product.rowAtPoint(e.getPoint());
                tbl_product.setRowSelectionInterval(selectRow, selectRow);
            }
        });

        popup_product.add("Güncelle").addActionListener(e -> {
            int selectId = (int) tbl_product.getValueAt(tbl_product.getSelectedRow(), 0);
            ProductUI productUI = new ProductUI(this.productController.getById(selectId));
            productUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadProductTable(null);
                }
            });

        });
        popup_product.add("Sil").addActionListener(e -> {
            int selectId = (int) tbl_product.getValueAt(tbl_product.getSelectedRow(), 0);

            if (Helper.confirm("sure")) {
                if (this.productController.delete(selectId)) {
                    Helper.showMsg("done");
                    loadProductTable(null);
                } else {
                    Helper.showMsg("error");

                }
            }
        });
        tbl_product.setComponentPopupMenu(popup_product);
    }


    private void loadProductTable(ArrayList<Product> products) {
        Object[] columnProduct = {"ID", "Ürün Adı", "Ürün Kodu", "Fiyat", "Stok"};
        if (products == null) {
            products = this.productController.findAll();
        }

        // Tablo Sıfırlama
        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_product.getModel();
        clearModel.setRowCount(0);

        this.tmdl_product.setColumnIdentifiers(columnProduct);
        for (Product product : products) {
            Object[] rowObject = {product.getId(), product.getName(), product.getCode(), product.getPrice(), product.getStock()

            };
            this.tmdl_product.addRow(rowObject);
        }
        this.tbl_product.setModel(tmdl_product);
        this.tbl_product.getTableHeader().setReorderingAllowed(false);
        this.tbl_product.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_product.setEnabled(true);

    }

    private void loadCustomerButtonEvent() {
        this.btn_customer_new.addActionListener(e -> {
            CustomerUI customerUI = new CustomerUI(new Customer());

            customerUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCustomerTable(null);
                }
            });
        });
        btn_customer_filter.addActionListener(e -> {
            ArrayList<Customer> filteredCustomers = this.customerController.filter(this.fld_f_customername.getText(), (Customer.TYPE) this.cmb_f_customer_type.getSelectedItem());

            loadCustomerTable(filteredCustomers);

        });

        btn_customer_filter_reset.addActionListener(e -> {
            loadCustomerTable(null);
            this.fld_f_customername.setText(null);
            this.cmb_f_customer_type.setSelectedItem(null);

        });
    }

    private void loadCustomerPopupMenu() {

        this.tbl_customer.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectRow = tbl_customer.rowAtPoint(e.getPoint());
                tbl_customer.setRowSelectionInterval(selectRow, selectRow);
            }
        });

        this.popup_customer.add("Güncelle").addActionListener(e -> {
            int selectId = (int) tbl_customer.getValueAt(tbl_customer.getSelectedRow(), 0);
            CustomerUI customerUI = new CustomerUI(this.customerController.getById(selectId));
            customerUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCustomerTable(null);

                }
            });
        });
        this.popup_customer.add("Sil").addActionListener(e -> {
            int selectId = (int) tbl_customer.getValueAt(tbl_customer.getSelectedRow(), 0);
            if (Helper.confirm("sure")) {
                if (this.customerController.delete(selectId)) {
                    Helper.showMsg("done");
                    loadCustomerTable(null);
                } else {
                    Helper.showMsg("error");
                }
            }
        });

        this.tbl_customer.setComponentPopupMenu(this.popup_customer);
    }

    private void loadCustomerTable(ArrayList<Customer> customers) {
        Object[] columnCustomer = {"ID", "Müşteri Adı", "Tipi", "Telefon", "E-Posta", "Adres"};
        if (customers == null) {
            customers = this.customerController.findAll();
        }

        // Tablo Sıfırlama
        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_customer.getModel();
        clearModel.setRowCount(0);

        this.tmdl_customer.setColumnIdentifiers(columnCustomer);
        for (Customer customer : customers) {
            Object[] rowObject = {customer.getId(), customer.getName(), customer.getType(), customer.getPhone(), customer.getMail(), customer.getAddress()};
            this.tmdl_customer.addRow(rowObject);
        }
        this.tbl_customer.setModel(tmdl_customer);
        this.tbl_customer.getTableHeader().setReorderingAllowed(false);
        this.tbl_customer.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_customer.setEnabled(true);

    }


}
