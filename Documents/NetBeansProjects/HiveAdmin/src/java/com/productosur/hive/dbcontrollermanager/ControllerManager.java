/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.dbcontrollermanager;

import com.productosur.hive.dbcontrollers.ClientsJpaController;
import com.productosur.hive.dbcontrollers.CurrenciesJpaController;
import com.productosur.hive.dbcontrollers.DailyRoutesJpaController;
import com.productosur.hive.dbcontrollers.DeliveriesJpaController;
import com.productosur.hive.dbcontrollers.DeliveryStockJpaController;
import com.productosur.hive.dbcontrollers.DiscountsJpaController;
import com.productosur.hive.dbcontrollers.DocumentypesJpaController;
import com.productosur.hive.dbcontrollers.EmployeesJpaController;
import com.productosur.hive.dbcontrollers.EmployeesRolesJpaController;
import com.productosur.hive.dbcontrollers.ManufacturersJpaController;
import com.productosur.hive.dbcontrollers.OrderlinesJpaController;
import com.productosur.hive.dbcontrollers.OrdersJpaController;
import com.productosur.hive.dbcontrollers.PaydocumentsJpaController;
import com.productosur.hive.dbcontrollers.PaymentsJpaController;
import com.productosur.hive.dbcontrollers.ProductsJpaController;
import com.productosur.hive.dbcontrollers.ProductscategoriesJpaController;
import com.productosur.hive.dbcontrollers.ProductstocksJpaController;
import com.productosur.hive.dbcontrollers.PurchasesJpaController;
import com.productosur.hive.dbcontrollers.PurchasesLinesJpaController;
import com.productosur.hive.dbcontrollers.SubsidiariesJpaController;
import com.productosur.hive.dbcontrollers.TaxesJpaController;
import com.productosur.hive.dbcontrollers.TrucksJpaController;
import com.productosur.hive.dbcontrollers.UsersJpaController;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Productosur
 */
public class ControllerManager {
    
    private static ControllerManager instance;
    private EntityManagerFactory emf;
    private ClientsJpaController clientsController;
    private DailyRoutesJpaController dailyRoutesJpaController;
    private DiscountsJpaController discountsJpaController;
    private EmployeesJpaController employeesJpaController;
    private EmployeesRolesJpaController employeesRolesJpaController;
    private UsersJpaController usersJpaController;
    private ManufacturersJpaController manufacturersJpaController;
    private OrdersJpaController ordersJpaController;
    private OrderlinesJpaController orderlinesJpaController;
    private ProductsJpaController productsJpaController;
    private ProductscategoriesJpaController productscategoriesJpaController;
    private SubsidiariesJpaController subsidiariesJpaController;
    private PaymentsJpaController paymentsJpaController;
    private PaydocumentsJpaController paydocumentsJpaController;
    private CurrenciesJpaController currenciesJpaController;
    private TaxesJpaController taxesJpaController;
    private PurchasesJpaController purchasesJpaController;
    private PurchasesLinesJpaController purchasesLinesJpaController;
    private DeliveriesJpaController deliveriesJpaController;
    private DeliveryStockJpaController deliveryStockJpaController;
    private TrucksJpaController trucksJpaController;
    private ProductstocksJpaController productstocksJpaController;
    private DocumentypesJpaController documentypesJpaController;
    

    public ControllerManager() {
        this.emf = Persistence.createEntityManagerFactory("HiveAdminPU");
        this.clientsController = new ClientsJpaController(emf);
        this.dailyRoutesJpaController = new DailyRoutesJpaController(emf);
        this.discountsJpaController = new DiscountsJpaController(emf);
        this.employeesJpaController = new EmployeesJpaController(emf);
        this.employeesRolesJpaController = new EmployeesRolesJpaController(emf);
        this.usersJpaController = new UsersJpaController(emf);
        this.manufacturersJpaController = new ManufacturersJpaController(emf);
        this.ordersJpaController = new OrdersJpaController(emf);
        this.orderlinesJpaController = new OrderlinesJpaController(emf);
        this.productsJpaController = new ProductsJpaController(emf);
        this.productscategoriesJpaController = new ProductscategoriesJpaController(emf);
        this.subsidiariesJpaController = new SubsidiariesJpaController(emf);
        this.paymentsJpaController = new PaymentsJpaController(emf);
        this.paydocumentsJpaController = new PaydocumentsJpaController(emf);
        this.currenciesJpaController = new CurrenciesJpaController(emf);
        this.taxesJpaController = new TaxesJpaController(emf);
        this.purchasesJpaController = new PurchasesJpaController(emf);
        this.purchasesLinesJpaController = new PurchasesLinesJpaController(emf);
        this.deliveriesJpaController = new DeliveriesJpaController(emf);
        this.deliveryStockJpaController = new DeliveryStockJpaController(emf);
        this.trucksJpaController = new TrucksJpaController(emf);
        this.productstocksJpaController = new ProductstocksJpaController(emf);
        this.documentypesJpaController = new DocumentypesJpaController(emf);
    }

    public static ControllerManager getInstance(){
        if(instance == null){
            instance = new ControllerManager();   
        }
        return instance;
    }

    public ClientsJpaController getClientsController() {
        return clientsController;
    }

    public DailyRoutesJpaController getDailyRoutesJpaController() {
        return dailyRoutesJpaController;
    }

    public DiscountsJpaController getDiscountsJpaController() {
        return discountsJpaController;
    }

    public EmployeesJpaController getEmployeesJpaController() {
        return employeesJpaController;
    }

    public EmployeesRolesJpaController getEmployeesRolesJpaController() {
        return employeesRolesJpaController;
    }

    public UsersJpaController getUsersJpaController() {
        return usersJpaController;
    }

    public ManufacturersJpaController getManufacturersJpaController() {
        return manufacturersJpaController;
    }

    public OrdersJpaController getOrdersJpaController() {
        return ordersJpaController;
    }

    public OrderlinesJpaController getOrderlinesJpaController() {
        return orderlinesJpaController;
    }

    public ProductsJpaController getProductsJpaController() {
        return productsJpaController;
    }

    public ProductscategoriesJpaController getProductscategoriesJpaController() {
        return productscategoriesJpaController;
    }
    
     public SubsidiariesJpaController getSubsidiariesJpaController() {
        return subsidiariesJpaController;
    }
     
    public PaymentsJpaController getPaymentsJpaController(){
        return paymentsJpaController;
    }
    
    public PaydocumentsJpaController getPaydocumentsJpaController(){
        return paydocumentsJpaController;
    }

    public CurrenciesJpaController getCurrenciesJpaController() {
        return currenciesJpaController;
    }

    public void setCurrenciesJpaController(CurrenciesJpaController currenciesJpaController) {
        this.currenciesJpaController = currenciesJpaController;
    }

    public TaxesJpaController getTaxesJpaController() {
        return taxesJpaController;
    }

    public void setTaxesJpaController(TaxesJpaController taxesJpaController) {
        this.taxesJpaController = taxesJpaController;
    }

    public PurchasesJpaController getPurchasesJpaController() {
        return purchasesJpaController;
    }

    public void setPurchasesJpaController(PurchasesJpaController purchasesJpaController) {
        this.purchasesJpaController = purchasesJpaController;
    }

    public PurchasesLinesJpaController getPurchasesLinesJpaController() {
        return purchasesLinesJpaController;
    }

    public void setPurchasesLinesJpaController(PurchasesLinesJpaController purchasesLinesJpaController) {
        this.purchasesLinesJpaController = purchasesLinesJpaController;
    }

    public DeliveriesJpaController getDeliveriesJpaController() {
        return deliveriesJpaController;
    }

    public void setDeliveriesJpaController(DeliveriesJpaController deliveriesJpaController) {
        this.deliveriesJpaController = deliveriesJpaController;
    }

    public DeliveryStockJpaController getDeliveryStockJpaController() {
        return deliveryStockJpaController;
    }

    public void setDeliveryStockJpaController(DeliveryStockJpaController deliveryStockJpaController) {
        this.deliveryStockJpaController = deliveryStockJpaController;
    }

    public TrucksJpaController getTrucksJpaController() {
        return trucksJpaController;
    }

    public void setTrucksJpaController(TrucksJpaController trucksJpaController) {
        this.trucksJpaController = trucksJpaController;
    }

    public ProductstocksJpaController getProductstocksJpaController() {
        return productstocksJpaController;
    }

    public void setProductstocksJpaController(ProductstocksJpaController productstocksJpaController) {
        this.productstocksJpaController = productstocksJpaController;
    }

    public DocumentypesJpaController getDocumentypesJpaController() {
        return documentypesJpaController;
    }

    public void setDocumentypesJpaController(DocumentypesJpaController documentypesJpaController) {
        this.documentypesJpaController = documentypesJpaController;
    }
  
}
