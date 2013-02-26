/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.managedbeans;

import com.productosur.hive.dbcontrollermanager.ControllerManager;
import com.productosur.hive.dbcontrollers.exceptions.NonexistentEntityException;
import com.productosur.hive.entities.Deliveries;
import com.productosur.hive.entities.DeliveryStock;
import com.productosur.hive.entities.Productstocks;
import com.productosur.hive.entities.Trucks;
import com.productosur.hive.managedbeans.converters.TruckConverter;
import com.productosur.hive.managedbeans.visualobjects.DeliveryStockVO;
import com.productosur.hive.util.JsfUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Productosur
 */
@ManagedBean(name="deliverymb")
@ViewScoped
public class DeliveryMB {

    private List<Productstocks> productStockList;
    private Productstocks selectedProductstocks;
    private DeliveryStock deliveryStock;
    private List<Deliveries> deliveriesList;
    private List<Trucks> truckList;
    private Deliveries selectedDelivery;
    private Trucks selectedTruck;
    private List<DeliveryStockVO> deliveryStockVOList;
    private DeliveryStockVO selectedDeliveryVO;
    private TruckConverter truckConverter;
    private Date deliveryDate;
    
    /**
     * Creates a new instance of DeliveryMB
     */
    public DeliveryMB() {
    }
    
    @PostConstruct
    public void inicialize(){
        productStockList = ControllerManager.getInstance().getProductstocksJpaController().findProductstocksEntities();
        deliveriesList = ControllerManager.getInstance().getDeliveriesJpaController().findDeliveriesEntities();
        truckList = ControllerManager.getInstance().getTrucksJpaController().findTrucksEntities();
        selectedProductstocks = new Productstocks();
        selectedTruck = new Trucks();
        truckConverter = new TruckConverter();
        createDeliveryStockVOList();
    }

    private void createDeliveryStockVOList(){
        this.deliveryStockVOList = new ArrayList<DeliveryStockVO>();
        for (Productstocks p : productStockList) {
            DeliveryStockVO vo = new DeliveryStockVO(p.getId(), p.getValue(), p.getValue(), p);
            deliveryStockVOList.add(vo);
        }
    }
    
    public List<Productstocks> getProductStockList() {
        return productStockList;
    }

    public void setProductStockList(List<Productstocks> productStockList) {
        this.productStockList = productStockList;
    }

    public Productstocks getSelectedProductstocks() {
        return selectedProductstocks;
    }

    public void setSelectedProductstocks(Productstocks selectedProductstocks) {
        this.selectedProductstocks = selectedProductstocks;
    }

    public DeliveryStock getDeliveryStock() {
        return deliveryStock;
    }

    public void setDeliveryStock(DeliveryStock deliveryStock) {
        this.deliveryStock = deliveryStock;
    }

    public List<Trucks> getTruckList() {
        return truckList;
    }

    public void setTruckList(List<Trucks> truckList) {
        this.truckList = truckList;
    }

    public Trucks getSelectedTruck() {
        return selectedTruck;
    }

    public void setSelectedTruck(Trucks selectedTruck) {
        this.selectedTruck = selectedTruck;
    }

    public List<DeliveryStockVO> getDeliveryStockVOList() {
        return deliveryStockVOList;
    }

    public void setDeliveryStockVOList(List<DeliveryStockVO> deliveryStockVOList) {
        this.deliveryStockVOList = deliveryStockVOList;
    }

    public DeliveryStockVO getSelectedDeliveryVO() {
        return selectedDeliveryVO;
    }

    public void setSelectedDeliveryVO(DeliveryStockVO selectedDeliveryVO) {
        this.selectedDeliveryVO = selectedDeliveryVO;
    }

    public TruckConverter getTruckConverter() {
        return truckConverter;
    }

    public void setTruckConverter(TruckConverter truckConverter) {
        this.truckConverter = truckConverter;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public List<Deliveries> getDeliveriesList() {
        return deliveriesList;
    }

    public void setDeliveriesList(List<Deliveries> deliveriesList) {
        this.deliveriesList = deliveriesList;
    }

    public Deliveries getSelectedDelivery() {
        return selectedDelivery;
    }

    public void setSelectedDelivery(Deliveries selectedDelivery) {
        this.selectedDelivery = selectedDelivery;
    }
    
//    public void handleDateSelect(SelectEvent event) {  
//        try {  
//            deliveryDate = new SimpleDateFormat("dd/MM/yyyy").parse(""+event.getObject());
//        } catch (ParseException ex) {
//            Logger.getLogger(PurchasesMB.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        
//    }  
    
    public void onEdit(RowEditEvent event) {  
        JsfUtil.getInstance().addSuccessMessage("Stock Modificado");//+((DeliveryStockVO) event.getObject()).getProductStockEntitie().getProductId().getName());
    }  
      
    public void onCancel(RowEditEvent event) {  
        JsfUtil.getInstance().addSuccessMessage("Modificación Cancelada");      
    }  
    
    public String saveDataAction(){
        
        try {
            Deliveries delivery = new Deliveries();
            delivery.setInitTime(deliveryDate);
            delivery.setTruckId(selectedTruck);
            ControllerManager.getInstance().getDeliveriesJpaController().create(delivery); 
                        
        for (DeliveryStockVO d : deliveryStockVOList){          
                Productstocks ps = ControllerManager.getInstance().getProductstocksJpaController().findProductstocks(d.getId());
                
                ps.setValue(ps.getValue()- d.getDeliveryStock());
                ps.setLastChange(Calendar.getInstance().getTime());
                ControllerManager.getInstance().getProductstocksJpaController().edit(ps);
                
                DeliveryStock ds = new DeliveryStock();
                ds.setInitialStock(d.getDeliveryStock());
                ds.setFinalStock(d.getDeliveryStock());
                ds.setProductId(d.getProductStockEntitie().getProductId());
                ds.setDeliveryId(delivery);
                
                ControllerManager.getInstance().getDeliveryStockJpaController().create(ds);
        }
        JsfUtil.getInstance().addSuccessMessage("Reparto Creado con Exito");
         } catch (NonexistentEntityException ex) {
                JsfUtil.getInstance().addErrorMessage("Error de Persistencia al Guardar Reparto");
                Logger.getLogger(DeliveryMB.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                JsfUtil.getInstance().addErrorMessage("Error al Guardar Reparto");
                Logger.getLogger(DeliveryMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        return "/index.xhtml";
    }
}
