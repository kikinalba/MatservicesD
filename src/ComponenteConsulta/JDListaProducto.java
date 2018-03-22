package ComponenteConsulta;

import ComponenteDatos.BD;
import beans.ProductoBean;
import ComponenteDatos.BDProducto;
import ComponenteDatos.ConfiguracionDAO;
import ComponenteReportes.ReporteProductoGeneral;
import beans.CategoriaBean;
import beans.DatosEmpresaBean;
import beans.ProductoBean;
import beans.UsuarioBean;
import constantes.ConstantesProperties;
import consumewebservices.WSDatosEmpresa;
import consumewebservices.WSInventarios;
import consumewebservices.WSInventariosList;
import consumewebservices.WSUsuarios;
import consumewebservices.WSUsuariosList;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import util.Util;
import vistas.FrmProducto;
import vistas.FrmUsuarios;

public class JDListaProducto extends javax.swing.JDialog {
    DatosEmpresaBean configuracionBean = new DatosEmpresaBean();
    DefaultTableModel LProducto = new DefaultTableModel();
    
    //WSUsuarios
    Properties constantes = new ConstantesProperties().getProperties();
    WSDatosEmpresa hiloEmpresa;
    //WSUsuarios
    WSInventariosList hiloInventariosList;
    WSInventarios hiloInventarios;
    //Fin WSUsuarios
    
    Map<String,String> sucursalesHMCons = new HashMap<String,String>();
    Map<String,String> proveedoresHMCons = new HashMap<String,String>();
    Map<String,String> categoriasHMCons = new HashMap<String,String>();

    public JDListaProducto(java.awt.Frame parent, boolean modal
            , Map<String,String> sucursalesHMCons
            , Map<String,String> categoriasHMCons
            , Map<String,String> proveedoresHMCons) {
        super(parent, modal);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        hiloEmpresa = new WSDatosEmpresa();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty(""
                + "GETDATOSEMPRESA");
        DatosEmpresaBean resultadoWS = hiloEmpresa.
                ejecutaWebService(rutaWS,"1");
        this.setTitle(resultadoWS.getNombreEmpresa());
        
        ArrayList<ProductoBean> resultWSArray = null;
        hiloInventariosList = new WSInventariosList();
        rutaWS = constantes.getProperty("IP") 
                + constantes.getProperty("GETINVENTARIOS");
        resultWSArray = hiloInventariosList.ejecutaWebService(rutaWS,"1");
        
        Util util = new Util();
        FrmProducto frmproductos = new FrmProducto();
        String titulos[] = {"ID", "CODIGO", "DESCRIPCIÓN", "$ COSTO", "$ PÚBLICO", "EXIST.", "EXIST. MIN","SUCURSAL", "CATEGORÍA", "PROVEEDOR"};
        LProducto.setColumnIdentifiers(titulos);
        for (ProductoBean p : resultWSArray) {
            String sucursal = util.buscaDescFromIdSuc(sucursalesHMCons, "" + p.getIdSucursal());
            String categoria = util.buscaDescFromIdCat(categoriasHMCons, "" + p.getIdCategoria());
            String proveedor = util.buscaDescFromIdProv(proveedoresHMCons, "" + p.getIdProveedor());
            String Datos[] = {""+p.getIdArticulo()
                    , p.getCodigo()
                    , p.getDescripcion()
                    , "" + p.getPrecioCosto()
                    , "" + p.getPrecioUnitario()
                    , "" + p.getExistencia()
                    , "" + p.getExistenciaMinima()
                    , sucursal
                    , categoria
                    , proveedor};
            LProducto.addRow(Datos);
        }
        
        initComponents();
        btnImprimir.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblConsultaProductos = new javax.swing.JTable();
        btnImprimir = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(70, 99, 138));

        jPanel2.setBackground(new java.awt.Color(247, 254, 255));

        jLabel1.setFont(new java.awt.Font("Garamond", 1, 24)); // NOI18N
        jLabel1.setText("LISTA DE PRODUCTOS");

        tblConsultaProductos.setModel(LProducto);
        jScrollPane1.setViewportView(tblConsultaProductos);

        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Print.png"))); // NOI18N
        btnImprimir.setText("IMPRIMIR");
        btnImprimir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnImprimir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exit.png"))); // NOI18N
        jButton1.setText("SALIR");
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(423, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(161, 161, 161))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addGap(52, 52, 52))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        this.dispose();
        repProdGral = new ReporteProductoGeneral();
        JOptionPane.showMessageDialog(null, "Espere por favor..., puede tardar unos segundos");
        repProdGral.runReporteProductoGeneral();
       
        //Esto imprime la tabla funciona pero hay que ajustar tamaño de tabla
//        try {
//            //Mensaje de encabezado
//            MessageFormat headerFormat = new MessageFormat("Inventario Farmacia Lux");            
//            //Mensaje en el pie de pagina
//            MessageFormat footerFormat = new MessageFormat("FLUX");
//            //Imprimir JTable
//            tblConsultaProductos.print(JTable.PrintMode.NORMAL, headerFormat, footerFormat);
//        } catch (PrinterException ex) {
//            JOptionPane.showMessageDialog(null, ex.getMessage());
//        }    
        
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
        FrmProducto frmProducto = new FrmProducto();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblConsultaProductos;
    // End of variables declaration//GEN-END:variables
    private ReporteProductoGeneral repProdGral;
}