package vistas;

import ComponenteConsulta.JDListaCorteDia;
import beans.ProveedorBean;
import ComponenteConsulta.JDListaProveedor;
import beans.ComprasBean;
import beans.DatosEmpresaBean;
import beans.DetalleCompraBean;
import beans.DetalleVentaBean;
import beans.FechaServidorBean;
import beans.ProductoBean;
import beans.UsuarioBean;
import beans.VentasBean;
import constantes.ConstantesProperties;
import consumewebservices.WSCompras;
import consumewebservices.WSComprasList;
import consumewebservices.WSDatosEmpresa;
import consumewebservices.WSDetalleComprasList;
import consumewebservices.WSDetalleVentasList;
import consumewebservices.WSInventarios;
import consumewebservices.WSInventariosList;
import consumewebservices.WSMovimientos;
import consumewebservices.WSVentas;
import consumewebservices.WSVentasList;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import util.Util;
import static vistas.Principal.productos;

public class FrmConsultaCompras extends javax.swing.JFrame {
    //WS
    Util util = new Util();
    Properties constantes = new ConstantesProperties().getProperties();
    WSDatosEmpresa hiloEmpresa;
    WSCompras hiloCompras;
    WSComprasList hiloComprasList;
    WSDetalleComprasList hiloDetalleComprasList;
    //Fin WS
    DateFormat fecha = DateFormat.getDateInstance();
    String accion = "";
    ArrayList<ComprasBean> comprasGlobal = null;
    ArrayList<DetalleCompraBean> detalleComprasGlobal = null;
    ArrayList<ProductoBean> inventario = null;

    public FrmConsultaCompras() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        // Actualizas tbl Ventas
        hiloComprasList = new WSComprasList();
        String rutaWS = constantes.getProperty("IP") 
                + constantes.getProperty("GETCOMPRAS");
        comprasGlobal = hiloComprasList.ejecutaWebService(rutaWS,"1");
        recargarTableCompras(comprasGlobal);

        inventario = util.getMapProductos();
        productos = util.getMapProductos();
        util.llenaMapProductos(productos);
        
        // Actualizas tbl DetalleVentas
        hiloDetalleComprasList = new WSDetalleComprasList();
        rutaWS = constantes.getProperty("IP") 
                + constantes.getProperty("GETDETALLECOMPRAS");
        detalleComprasGlobal = hiloDetalleComprasList.ejecutaWebService(rutaWS,"1");
        recargarTableDetalleCompras(detalleComprasGlobal);
        
        lblUsuario.setText("Usuario : " + Ingreso.usuario.getNombre()
            + " " + Ingreso.usuario.getApellido_paterno()
            + " " + Ingreso.usuario.getApellido_materno());
        
//        //inhabilita combos
//        cboSucursal.setEnabled(false);
//        cboProveedor.setEnabled(false);
//        cboCategoriaPro.setEnabled(false);
//
//        //cambia formato de fecha a tipo datetime xq asi esta en bd remota
//        jCalFechaIngresoProd.setDate(new Date());
////        jCalFechaIngresoProd.setDateFormatString("yyyy-MM-dd HH:mm:ss");
//        
//        txtIdArticulo.setVisible(false);
//        btnGuardarPro.setEnabled(false);
        
        this.setTitle(Principal.datosEmpresaBean.getNombreEmpresa());
        this.setIcon();
        
////        if (this.getLlamadoVentaInventario() == 1) {
////            btnNuevoPro.setVisible(true);
////            btnGuardarPro.setEnabled(true);
////            accion = "Guardar";
////            btnModificarPro.setVisible(false);
////            btnEliminarPro.setVisible(false);
////        }
//        
        limpiaTblDetalleCompra();        
    }
    
    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("..\\img\\matserviceslogo.png")));
    }

    //Para Tabla Ventas
    public void recargarTableCompras(ArrayList<ComprasBean> list) {
        Object[][] datos = new Object[list.size()][6];
        int i = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMMM-yyyy");
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//System.out.println(dateFormat.format(new Date()));        
        for (ComprasBean p : list) {
            datos[i][0] = p.getIdCompra();
            datos[i][1] = dateFormat.format(p.getFecha());
            datos[i][2] = p.getFactura();
//            datos[i][1] = p.getFecha();
            datos[i][3] = util.buscaDescFromIdProv(Principal.proveedoresHM
                    , "" + p.getIdProveedor());
            datos[i][4] = util.buscaDescFromIdSuc(Principal.sucursalesHM 
                    , "" + p.getIdSucursal());
            datos[i][5] = util.buscaDescFromIdUsu(Principal.usuariosHM 
                    , "" + p.getIdUsuario());
            i++;
        }
        tblConsultaCompras.setModel(new javax.swing.table.DefaultTableModel(
                datos,
                new String[]{
                    "No. COMPRA", "FECHA COMPRA", "FACTURA" 
                        , "PROVEEDOR", "SUCURSAL", "USUARIO"
                }) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    } 

    //Para Tabla DetalleVenta
    public void recargarTableDetalleCompras(ArrayList<DetalleCompraBean> list) {
        Object[][] datos = new Object[list.size()][8];
        int i = 0;
        for (DetalleCompraBean p : list) {
//            if ((Ingreso.usuario.getIdSucursal() == p.getIdSucursal()) ||
//                    (Ingreso.usuario.getUsuario().equalsIgnoreCase(constantes.getProperty("SUPERUSUARIO")))) {
                datos[i][0] = p.getIdDetalleCompra();
                datos[i][1] = p.getIdCompra();
                datos[i][2] = util.buscaDescFromIdProd(Principal.productosHMID, 
                        "" + p.getIdArticulo());
                datos[i][3] = p.getPrecioPublico();
                datos[i][4] = p.getPrecioCosto();
                datos[i][5] = p.getCantidad();
                datos[i][6] = p.getDescuento();
                datos[i][7] = util.buscaDescFromIdSuc(Principal.sucursalesHM
                        , "" + p.getIdSucursal());
                i++;
//            }
        }
//        Object[][] datosFinal = new Object[i][7];
//        //Para filtrar los registros
//        for (int j=0; j<i; j++) {
//            if (datos[j][0]!=null) {
//                datosFinal[j][0] = datos[j][0];
//                datosFinal[j][1] = datos[j][1];
//                datosFinal[j][2] = datos[j][2];
//                datosFinal[j][3] = datos[j][3];
//                datosFinal[j][4] = datos[j][4];
//                datosFinal[j][5] = datos[j][5];
//                datosFinal[j][6] = datos[j][6];
//            }
//        }
        //Fin Para filtrar los registros
        tblConsultaDetalleCompra.setModel(new javax.swing.table.DefaultTableModel(
                datos,
                new String[]{ 
                    "ID","No. COMPRA", "PRODUCTO", "$ PÚB."
                    , "$ COSTO", "CANTIDAD", "DESCUENTO", "SUCURSAL"
                }) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        tblConsultaDetalleCompra.getColumnModel().getColumn(0).setPreferredWidth(0);
        tblConsultaDetalleCompra.getColumnModel().getColumn(0).setMaxWidth(0);
    } 
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtBuscarCompra = new javax.swing.JTextField();
        cboParametroVentas = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblConsultaDetalleCompra = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jCalFechaIni = new com.toedter.calendar.JDateChooser();
        jCalFechaFin = new com.toedter.calendar.JDateChooser();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblConsultaCompras = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        lblUsuario = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(70, 99, 138));

        jPanel2.setBackground(new java.awt.Color(247, 254, 255));

        jLabel1.setFont(new java.awt.Font("Garamond", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 204));
        jLabel1.setText("CONSULTA DE COMPRAS");

        txtBuscarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarCompraActionPerformed(evt);
            }
        });
        txtBuscarCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarCompraKeyReleased(evt);
            }
        });

        cboParametroVentas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No. Compra", "Proveedor", "Sucursal", "Usuario", "Factura" }));
        cboParametroVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboParametroVentasActionPerformed(evt);
            }
        });

        tblConsultaDetalleCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Codigo", "RFC", "Nombre"
            }
        ));
        tblConsultaDetalleCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblConsultaDetalleCompraMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblConsultaDetalleCompra);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exit.png"))); // NOI18N
        jButton1.setText("SALIR");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Fecha Inicio :");

        jLabel3.setText("Fecha Fin :");

        jCalFechaIni.setDateFormatString("yyyy-MM-d");

        jCalFechaFin.setDateFormatString("yyyy-MM-d");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/List.png"))); // NOI18N
        jButton2.setText("MOSTRAR");
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/report2.png"))); // NOI18N
        jButton4.setText("CORTE CAJA");
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jCalFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCalFechaIni, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jCalFechaIni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton2))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jCalFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        tblConsultaCompras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Codigo", "RFC", "Nombre"
            }
        ));
        tblConsultaCompras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblConsultaComprasMouseClicked(evt);
            }
        });
        tblConsultaCompras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblConsultaComprasKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblConsultaCompras);

        jLabel4.setFont(new java.awt.Font("Garamond", 1, 24)); // NOI18N
        jLabel4.setText("COMPRA");

        jLabel5.setFont(new java.awt.Font("Garamond", 1, 24)); // NOI18N
        jLabel5.setText("DETALLE DE LA COMPRA");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Erase.png"))); // NOI18N
        jButton3.setText("CANCELAR");
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        lblUsuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblUsuario.setText("Usuario:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtBuscarCompra)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboParametroVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)))
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(114, 114, 114))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUsuario)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(103, 103, 103))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(15, 15, 15)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtBuscarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cboParametroVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jButton1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(lblUsuario))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 993, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboParametroVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboParametroVentasActionPerformed

    }//GEN-LAST:event_cboParametroVentasActionPerformed

    private void txtBuscarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarCompraActionPerformed
    }//GEN-LAST:event_txtBuscarCompraActionPerformed

    private void txtBuscarCompraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarCompraKeyReleased
        actualizarBusquedaCompra();
    }//GEN-LAST:event_txtBuscarCompraKeyReleased

    private void tblConsultaDetalleCompraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblConsultaDetalleCompraMouseClicked
    }//GEN-LAST:event_tblConsultaDetalleCompraMouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
    }//GEN-LAST:event_formWindowClosed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
        FrmInventario inventario = new FrmInventario();
//        inventario.setExtendedState(inventario.MAXIMIZED_BOTH);
//        inventario.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblConsultaComprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblConsultaComprasMouseClicked
        actualizarBusquedaDetalleCompra();
    }//GEN-LAST:event_tblConsultaComprasMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        limpiaTblDetalleCompra();        
        String fechaIni = "";
        String fechaFin = "";
        //Tomamos las dos fechas y las convierto a java.sql.date
        java.util.Date fechaUtilDateIni = jCalFechaIni.getDate();
        java.util.Date fechaUtilDateFin = jCalFechaFin.getDate();
        java.sql.Date fechaSqlDateIni;
        java.sql.Date fechaSqlDateFin;
        try {
            fechaSqlDateIni = new java.sql.Date(fechaUtilDateIni.getTime());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar por lo menos la fecha de Inicio");
            return;
        }
        try {
            fechaSqlDateFin = new java.sql.Date(fechaUtilDateFin.getTime());
        } catch (Exception e) {
            fechaSqlDateFin = fechaSqlDateIni;
        }
        fechaIni = fechaSqlDateIni.toString();
        fechaFin = fechaSqlDateFin.toString();
        if (fechaSqlDateIni.getTime() > fechaSqlDateFin.getTime()) {
            JOptionPane.showMessageDialog(null, "Fechas Incorrectas");
            return;
        }
        // Actualizas tbl Ventas
        ArrayList<ComprasBean> comprasPorFechas = null;
        hiloComprasList = new WSComprasList();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty("GETCOMPRASPORFECHASFINI") + fechaIni +
                constantes.getProperty("GETCOMPRASPORFECHASFFIN") + fechaFin;
        comprasPorFechas = hiloComprasList.ejecutaWebService(rutaWS,"2");
        recargarTableCompras(comprasPorFechas);
    }//GEN-LAST:event_jButton2ActionPerformed

    public void limpiaTblDetalleCompra() {
        recargarTableDetalleCompras(detalleComprasGlobal);
    }
    
    public void borrar() {
        limpiaTblDetalleCompra();        
        //LIMPIA TXT BUSQUEDA VENTAS
        txtBuscarCompra.setText("");
        actualizarBusquedaCompra();
        
        //limpia jcalendars
        jCalFechaIni.setDate(null);
        jCalFechaFin.setDate(null);           
    }
    
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        borrar();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tblConsultaComprasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblConsultaComprasKeyReleased
        actualizarBusquedaDetalleCompra();
    }//GEN-LAST:event_tblConsultaComprasKeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        limpiaTblDetalleCompra();        
        //Tomamos las dos fechas y las convierto a java.sql.date
        java.util.Date fechaUtilDateIni = jCalFechaIni.getDate();
        java.util.Date fechaUtilDateFin = jCalFechaFin.getDate();
        java.sql.Date fechaSqlDateIni;
        java.sql.Date fechaSqlDateFin;
        try {
            fechaSqlDateIni = new java.sql.Date(fechaUtilDateIni.getTime());
        } catch (Exception e) {
            Calendar calendar = Calendar.getInstance();
            java.util.Date currentDate = calendar.getTime();
            java.sql.Date date = new java.sql.Date(currentDate.getTime());            
            fechaSqlDateIni = date;
        }
        try {
            fechaSqlDateFin = new java.sql.Date(fechaUtilDateFin.getTime());
        } catch (Exception e) {
            fechaSqlDateFin = fechaSqlDateIni;
        }
        
        if (fechaSqlDateIni.getTime() > fechaSqlDateFin.getTime()) {
            JOptionPane.showMessageDialog(null, "Fechas Incorrectas");
            return;
        }
        
        JDListaCorteDia jdListaCorteDia = new JDListaCorteDia(this, true, fechaSqlDateIni, fechaSqlDateFin);
        jdListaCorteDia.setVisible(true);        
    }//GEN-LAST:event_jButton4ActionPerformed

    public void actualizarBusquedaCompra() {
        ArrayList<ComprasBean> resultWS = null;
        ProductoBean producto = null;
        //No. Venta, Cliente, Sucursal, Usuario
        if (String.valueOf(cboParametroVentas.getSelectedItem()).
                equalsIgnoreCase("No. Compra")) {
            if (txtBuscarCompra.getText().equalsIgnoreCase("")) {
                resultWS = comprasGlobal;
                recargarTableDetalleCompras(detalleComprasGlobal);
            } else {
                resultWS = llenaTablaCompras(
                        txtBuscarCompra.getText().trim(),0);
            }
        }
        if (String.valueOf(cboParametroVentas.getSelectedItem()).
                equalsIgnoreCase("Proveedor")) {
            if (txtBuscarCompra.getText().equalsIgnoreCase("")) {
                resultWS = comprasGlobal;
                recargarTableDetalleCompras(detalleComprasGlobal);
            } else {
                resultWS = llenaTablaCompras(
                        txtBuscarCompra.getText().trim(),1);
            }
        } 
        if (String.valueOf(cboParametroVentas.getSelectedItem()).
                equalsIgnoreCase("Sucursal")) {
            if (txtBuscarCompra.getText().equalsIgnoreCase("")) {
                resultWS = comprasGlobal;
                recargarTableDetalleCompras(detalleComprasGlobal);
            } else {
                resultWS = llenaTablaCompras(
                        txtBuscarCompra.getText().trim(),2);
            }
        } 
        if (String.valueOf(cboParametroVentas.getSelectedItem()).
                equalsIgnoreCase("Usuario")) {
            if (txtBuscarCompra.getText().equalsIgnoreCase("")) {
                resultWS = comprasGlobal;
                recargarTableDetalleCompras(detalleComprasGlobal);
            } else {
                resultWS = llenaTablaCompras(
                        txtBuscarCompra.getText().trim(),3);
            }
        } 
        if (String.valueOf(cboParametroVentas.getSelectedItem()).
                equalsIgnoreCase("Factura")) {
            if (txtBuscarCompra.getText().equalsIgnoreCase("")) {
                resultWS = comprasGlobal;
                recargarTableDetalleCompras(detalleComprasGlobal);
            } else {
                resultWS = llenaTablaCompras(
                        txtBuscarCompra.getText().trim(),4);
            }
        } 
        if (txtBuscarCompra.getText().equalsIgnoreCase("")) {
            resultWS = comprasGlobal;
            recargarTableDetalleCompras(detalleComprasGlobal);
        } else {
//                    resultWS = llenaTablaCompras(
//                            txtBuscarVenta.getText().trim(),3);
        }
        recargarTableCompras(resultWS);
    }
    
    private ArrayList<ComprasBean> llenaTablaCompras(String buscar, int tipoBusq) {
        ArrayList<ComprasBean> resultWS = new ArrayList<ComprasBean>();
        ComprasBean compra = null;
        for (int i=0; i<tblConsultaCompras.getModel().getRowCount(); i++) {
            String campoBusq = "";
            switch (tipoBusq) {
                case 0 : campoBusq = tblConsultaCompras.getModel().getValueAt(
                    i,0).toString();
                    break;
                case 1 : campoBusq = tblConsultaCompras.getModel().getValueAt(
                    i,3).toString();
                    break;
                case 2 : campoBusq = tblConsultaCompras.getModel().getValueAt(
                    i,4).toString().toLowerCase();
                    buscar = buscar.toLowerCase();
                    break;
                case 3 : campoBusq = tblConsultaCompras.getModel().getValueAt(
                    i,5).toString().toLowerCase();
                    buscar = buscar.toLowerCase();
                    break;
                case 4 : campoBusq = tblConsultaCompras.getModel().getValueAt(
                    i,2).toString().toLowerCase();
                    buscar = buscar.toLowerCase();
                    break;
            }
            if (campoBusq.indexOf(buscar)>=0) {
                compra = new ComprasBean();
                compra.setIdCompra(Integer.parseInt(tblConsultaCompras.getModel().getValueAt(i,0).toString()));
                
                String fecha = String.valueOf(tblConsultaCompras.getModel().getValueAt(i,1));
                compra.setFecha(util.stringToDate(fecha));
                compra.setFactura(String.valueOf(tblConsultaCompras.getModel().getValueAt(i,2)));
                compra.setIdProveedor(util.buscaIdProv(Principal.proveedoresHM
                        , tblConsultaCompras.getModel().getValueAt(i,3).toString()));
                int idSuc = util.buscaIdSuc(Principal.sucursalesHM
                        , "" + tblConsultaCompras
                                .getModel().getValueAt(i,4).toString());
                compra.setIdSucursal(idSuc);
                compra.setIdUsuario(util.buscaIdUsuario(Principal.usuariosHM
                        , "" + tblConsultaCompras
                                .getModel().getValueAt(i,5).toString()));
                resultWS.add(compra);
            }
        }
        return resultWS;
    }

    public void actualizarBusquedaDetalleCompra() {
        recargarTableDetalleCompras(detalleComprasGlobal);
        ArrayList<DetalleCompraBean> resultWS = null;
        ProductoBean producto = null;
        String idCompra = tblConsultaCompras.getModel()
                .getValueAt(tblConsultaCompras.getSelectedRow(),0).toString();
        resultWS = llenaTablaDetalleCompras(idCompra.trim(),0);
//        if (txtBuscarVenta.getText().equalsIgnoreCase("")) {
//            resultWS = detalleComprasGlobal;
//        }
        recargarTableDetalleCompras(resultWS);
    }
    
    private ArrayList<DetalleCompraBean> llenaTablaDetalleCompras(String buscar, int tipoBusq) {
        ArrayList<DetalleCompraBean> resultWS = new ArrayList<DetalleCompraBean>();
        DetalleCompraBean detalleCompra = null;
        for (int i=0; i<tblConsultaDetalleCompra.getModel().getRowCount(); i++) {
            String campoBusq = "";
            switch (tipoBusq) {
                case 0 : campoBusq = tblConsultaDetalleCompra.getModel().getValueAt(
                    i,1).toString().toLowerCase();
                    buscar = buscar.toLowerCase();
                    break;
            }
            if (campoBusq.indexOf(buscar)>=0) {
                detalleCompra = new DetalleCompraBean();
                detalleCompra.setIdDetalleCompra(Integer.parseInt(tblConsultaDetalleCompra.getModel().getValueAt(i,0).toString()));
                detalleCompra.setIdCompra(Integer.parseInt(tblConsultaDetalleCompra.getModel().getValueAt(i,1).toString()));
                detalleCompra.setIdArticulo(util.buscaIdProd(
                        Principal.productosHM, tblConsultaDetalleCompra.getModel().getValueAt(i,2).toString()));
                detalleCompra.setPrecioPublico(Double.parseDouble(tblConsultaDetalleCompra.getModel().getValueAt(i,3).toString()));
                detalleCompra.setPrecioCosto(Double.parseDouble(tblConsultaDetalleCompra.getModel().getValueAt(i,4).toString()));
                detalleCompra.setCantidad(Double.parseDouble(tblConsultaDetalleCompra.getModel().getValueAt(i,5).toString()));
                detalleCompra.setDescuento(Double.parseDouble(tblConsultaDetalleCompra.getModel().getValueAt(i,6).toString()));
                detalleCompra.setIdSucursal(util.buscaIdSuc(Principal.sucursalesHM
                        , tblConsultaDetalleCompra.getModel().getValueAt(i,7).toString()));
                resultWS.add(detalleCompra);
            }
        }
        return resultWS;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cboParametroVentas;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private com.toedter.calendar.JDateChooser jCalFechaFin;
    private com.toedter.calendar.JDateChooser jCalFechaIni;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTable tblConsultaCompras;
    private javax.swing.JTable tblConsultaDetalleCompra;
    private javax.swing.JTextField txtBuscarCompra;
    // End of variables declaration//GEN-END:variables
}