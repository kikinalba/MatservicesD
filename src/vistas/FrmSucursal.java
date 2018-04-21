package vistas;

import beans.ClienteBean;
import ComponenteConsulta.JDListaClientes;
import beans.DatosEmpresaBean;
import beans.EdoMunBean;
import beans.ProductoBean;
import beans.UsuarioBean;
import constantes.ConstantesProperties;
import consumewebservices.WSClientes;
import consumewebservices.WSClientesList;
import consumewebservices.WSDatosEmpresa;
import consumewebservices.WSInventarios;
import consumewebservices.WSUsuarios;
import consumewebservices.WSUsuariosList;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import util.Util;
import static vistas.Principal.estadosMun;
import static vistas.Principal.productos;

public class FrmSucursal extends javax.swing.JFrame {
    //WSUsuarios
    Util util = new Util();
    Properties constantes = new ConstantesProperties().getProperties();
    WSDatosEmpresa hiloEmpresa;
    //WSUsuarios
    WSClientesList hiloClientesList;
    WSClientes hiloClientes;
    //Fin WSUsuarios
    
    DatosEmpresaBean configuracionBean = new DatosEmpresaBean();

    String accion = "";
    
    private int llamadoVenta;

    public int getLlamadoVenta() {
        return llamadoVenta;
    }

    public void setLlamadoVenta(int llamadoVenta) {
        this.llamadoVenta = llamadoVenta;
    }

    public FrmSucursal(int llamadoVenta) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setLlamadoVenta(llamadoVenta);
        initComponents();
        hiloEmpresa = new WSDatosEmpresa();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty(""
                + "GETDATOSEMPRESA");
        DatosEmpresaBean configuracionBean = hiloEmpresa.
                ejecutaWebService(rutaWS,"1");
        actualizarBusqueda();
        activarBotones(true);

        //carga estados
        Iterator it = Principal.estadosHM.keySet().iterator();
        while(it.hasNext()){
          Object key = it.next();
          cboEstados.addItem(Principal.estadosHM.get(key));
        }
        
        lblUsuario.setText("Usuario : " 
                + Ingreso.usuario.getNombre()
                + " " + Ingreso.usuario.getApellido_paterno()
                + " " + Ingreso.usuario.getApellido_materno());
        this.setTitle(configuracionBean.getNombreEmpresa());
        this.setLocationRelativeTo(null);
        cboEstados.setEnabled(false);
        cboMunicipio.setEnabled(false);
        
        btnNuevoCli.setEnabled(true);
        btnGuardarCli.setEnabled(false);
        //btnEliminarCli.setEnabled(true);
        //btnModificarCli.setEnabled(false);
        btnCancelarCli.setEnabled(true);
        lblIdCliente.setText("");
//        JOptionPane.showMessageDialog(null, this.getLlamadoVenta());
        if (this.getLlamadoVenta() == 1) {
            btnNuevoCli.setVisible(true);
            btnGuardarCli.setEnabled(true);
            accion = "Guardar";
            btnModificarCli.setVisible(false);
            btnEliminarCli.setVisible(false);
        }
    }
    
    public void limpiarCajatexto() {
        cboEstados.setSelectedIndex(0);
        cboMunicipio.setSelectedIndex(0);
        lblIdCliente.setText("");
        txtEmpresa.setText("");
        txtRFC.setText("");
        txtNombreCli.setText("");
        txtApellidos.setText("");
        txtDireccion1.setText("");
        txtDireccion2.setText("");
        txtTelefonoCasa.setText("");
        txtTelefonoCelular.setText("");
        txtEmail.setText("");
        txtCP.setText("");
        txtNoCuenta.setText("");
        txtComentarios.setText("");
        lblIdCliente.setText("");
    }

    public void activarCajatexto(boolean b) {
        txtEmpresa.setEditable(b);
        txtRFC.setEditable(b);
        txtNombreCli.setEditable(b);
        txtApellidos.setEditable(b);
        txtDireccion1.setEditable(b);
        txtDireccion2.setEditable(b);
        txtTelefonoCasa.setEditable(b);
        txtTelefonoCelular.setEditable(b);
        txtEmail.setEditable(b);
        cboEstados.setEnabled(b);
        cboMunicipio.setEnabled(b);
        txtCP.setEditable(b);
        txtNoCuenta.setEditable(b);
        txtComentarios.setEditable(b);
        btnNuevoCli.setEnabled(false);
    }
    
    public void activarBotones(boolean b){
        btnNuevoCli.setEnabled(b);
        btnGuardarCli.setEnabled(!b);
        //btnEliminarCli.setEnabled(b);
        //btnModificarCli.setEnabled(!b);
        btnCancelarCli.setEnabled(!b);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtBuscarCli = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cboParametroCli = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtCliente = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        btnNuevoCli = new javax.swing.JButton();
        btnGuardarCli = new javax.swing.JButton();
        btnModificarCli = new javax.swing.JButton();
        btnCancelarCli = new javax.swing.JButton();
        btnMostrarCli = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtEmpresa = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtApellidos = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtRFC = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtNombreCli = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtDireccion1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtTelefonoCasa = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtTelefonoCelular = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtDireccion2 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cboEstados = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        cboMunicipio = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        txtCP = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtNoCuenta = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtComentarios = new javax.swing.JTextField();
        lblIdCliente = new javax.swing.JLabel();
        btnSalirCli = new javax.swing.JButton();
        btnEliminarCli = new javax.swing.JButton();
        lblUsuario = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(70, 99, 138));

        jPanel2.setBackground(new java.awt.Color(247, 254, 255));

        txtBuscarCli.setForeground(new java.awt.Color(255, 0, 0));
        txtBuscarCli.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBuscarCli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtBuscarCliMouseClicked(evt);
            }
        });
        txtBuscarCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarCliKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Garamond", 1, 12)); // NOI18N
        jLabel3.setText("BUSCAR CLIENTE");

        cboParametroCli.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Id", "Nombre" }));
        cboParametroCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboParametroCliActionPerformed(evt);
            }
        });

        jtCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "APELLIDOS", "NOMBRE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jtCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtClienteMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jtClienteMouseEntered(evt);
            }
        });
        jtCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtClienteKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jtCliente);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jLabel3))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(txtBuscarCli, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboParametroCli, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboParametroCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(247, 254, 255));

        jLabel4.setFont(new java.awt.Font("Garamond", 1, 20)); // NOI18N
        jLabel4.setText("REGISTRAR CLIENTE");

        btnNuevoCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/New document_1.png"))); // NOI18N
        btnNuevoCli.setText("NUEVO");
        btnNuevoCli.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNuevoCli.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNuevoCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoCliActionPerformed(evt);
            }
        });

        btnGuardarCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Save.png"))); // NOI18N
        btnGuardarCli.setText("GUARDAR");
        btnGuardarCli.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardarCli.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCliActionPerformed(evt);
            }
        });

        btnModificarCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Modify.png"))); // NOI18N
        btnModificarCli.setText("MODIFICAR");
        btnModificarCli.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnModificarCli.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnModificarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarCliActionPerformed(evt);
            }
        });

        btnCancelarCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Erase.png"))); // NOI18N
        btnCancelarCli.setText("CANCELAR");
        btnCancelarCli.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancelarCli.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCancelarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarCliActionPerformed(evt);
            }
        });

        btnMostrarCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/List.png"))); // NOI18N
        btnMostrarCli.setText("MOSTRAR");
        btnMostrarCli.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMostrarCli.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMostrarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarCliActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(247, 254, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Ingresar los datos del Cliente"));

        jLabel2.setText("Empresa :");

        txtEmpresa.setEditable(false);
        txtEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmpresaActionPerformed(evt);
            }
        });

        jLabel5.setText("Apellidos (*):");

        txtApellidos.setEditable(false);
        txtApellidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidosActionPerformed(evt);
            }
        });
        txtApellidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidosKeyTyped(evt);
            }
        });

        jLabel6.setText("RFC :");

        txtRFC.setEditable(false);
        txtRFC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRFCActionPerformed(evt);
            }
        });
        txtRFC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRFCKeyTyped(evt);
            }
        });

        jLabel7.setText("Nombre (*):");

        txtNombreCli.setEditable(false);
        txtNombreCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreCliActionPerformed(evt);
            }
        });

        jLabel8.setText("Dirección 1 :");

        txtDireccion1.setEditable(false);
        txtDireccion1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccion1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Teléfono Casa :");

        txtTelefonoCasa.setEditable(false);
        txtTelefonoCasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoCasaActionPerformed(evt);
            }
        });
        txtTelefonoCasa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoCasaKeyTyped(evt);
            }
        });

        jLabel9.setText("Teléfono Celular : ");

        txtTelefonoCelular.setEditable(false);
        txtTelefonoCelular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoCelularActionPerformed(evt);
            }
        });
        txtTelefonoCelular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoCelularKeyTyped(evt);
            }
        });

        jLabel10.setText("Email :");

        txtEmail.setEditable(false);
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        jLabel11.setText("Direccion 2 :");

        txtDireccion2.setEditable(false);
        txtDireccion2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccion2ActionPerformed(evt);
            }
        });

        jLabel12.setText("Estado :");

        cboEstados.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar..." }));
        cboEstados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEstadosActionPerformed(evt);
            }
        });
        cboEstados.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cboEstadosKeyTyped(evt);
            }
        });

        jLabel13.setText("Municipio :");

        cboMunicipio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar..." }));
        cboMunicipio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMunicipioActionPerformed(evt);
            }
        });

        jLabel14.setText("CP :");

        txtCP.setEditable(false);
        txtCP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCPActionPerformed(evt);
            }
        });

        jLabel15.setText("No. Cuenta :");

        txtNoCuenta.setEditable(false);
        txtNoCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoCuentaActionPerformed(evt);
            }
        });

        jLabel16.setText("Comentarios :");

        txtComentarios.setEditable(false);
        txtComentarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtComentariosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNombreCli, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtDireccion1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(14, 14, 14)
                                .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(txtDireccion2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addGap(10, 10, 10)
                        .addComponent(txtEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addComponent(txtRFC, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel6))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtTelefonoCasa, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTelefonoCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboEstados, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNoCuenta))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCP, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtComentarios)))))
                .addContainerGap(274, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblIdCliente)
                .addGap(230, 230, 230))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtRFC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtNombreCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtDireccion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtDireccion2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTelefonoCasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtTelefonoCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(cboEstados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(cboMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtCP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtNoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(txtComentarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(lblIdCliente)
                .addContainerGap())
        );

        btnSalirCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exit.png"))); // NOI18N
        btnSalirCli.setText("SALIR");
        btnSalirCli.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalirCli.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalirCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirCliActionPerformed(evt);
            }
        });

        btnEliminarCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Cancel.png"))); // NOI18N
        btnEliminarCli.setText("ELIMINAR");
        btnEliminarCli.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEliminarCli.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEliminarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCliActionPerformed(evt);
            }
        });

        lblUsuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblUsuario.setText("Usuario:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnNuevoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardarCli, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarCli, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificarCli, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelarCli)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMostrarCli)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalirCli))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(162, 162, 162)
                        .addComponent(lblUsuario))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(lblUsuario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalirCli, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                    .addComponent(btnGuardarCli, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                    .addComponent(btnNuevoCli, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                    .addComponent(btnModificarCli, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                    .addComponent(btnCancelarCli, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                    .addComponent(btnMostrarCli, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                    .addComponent(btnEliminarCli, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 666, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarCliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBuscarCliMouseClicked
    }//GEN-LAST:event_txtBuscarCliMouseClicked

    private void btnSalirCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirCliActionPerformed
        this.setLlamadoVenta(0);
        this.dispose();
    }//GEN-LAST:event_btnSalirCliActionPerformed

    private void btnMostrarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarCliActionPerformed
        JDListaClientes jdlCliente = new JDListaClientes(this,true
                ,Principal.municipiosHM,Principal.estadosHM);
        jdlCliente.setVisible(true);
    }//GEN-LAST:event_btnMostrarCliActionPerformed

    private void txtBuscarCliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarCliKeyReleased
        actualizarBusqueda();
    }//GEN-LAST:event_txtBuscarCliKeyReleased

    private void cboParametroCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboParametroCliActionPerformed
        // TODO add your handling code here:
        actualizarBusqueda();
    }//GEN-LAST:event_cboParametroCliActionPerformed

    private void btnNuevoCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoCliActionPerformed
        limpiarCajatexto();
        activarCajatexto(true);
        activarBotones(false);
        accion = "Guardar";
        txtEmpresa.requestFocus();
    }//GEN-LAST:event_btnNuevoCliActionPerformed

    private void btnCancelarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarCliActionPerformed
        limpiarCajatexto();
        activarCajatexto(false);
        activarBotones(true);
        btnCancelarCli.setEnabled(true);
    }//GEN-LAST:event_btnCancelarCliActionPerformed

    private void btnGuardarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCliActionPerformed
        if (accion.equalsIgnoreCase("Guardar")) {
            if (txtNombreCli.getText().compareTo("") != 0 
                    && txtEmpresa.getText().compareTo("") != 0
                    && txtRFC.getText().compareTo("") != 0
                    && txtEmpresa.getText().compareTo("") != 0
                    && txtApellidos.getText().compareTo("") != 0
                    && txtDireccion1.getText().compareTo("") != 0
                    && txtTelefonoCasa.getText().compareTo("") != 0
                    && !cboEstados.getSelectedItem().toString().
                    equalsIgnoreCase("Seleccionar...")
                    && !cboMunicipio.getSelectedItem().toString().
                    equalsIgnoreCase("Seleccionar...")
                    && txtEmpresa.getText().compareTo("") != 0
                        ) {
                    ClienteBean cli = new ClienteBean();
                    cli.setEmpresa(txtEmpresa.getText());
                    cli.setRfc(txtRFC.getText());
                    cli.setNombre(txtNombreCli.getText());
                    cli.setApellidos(txtApellidos.getText());
                    cli.setDireccion1(txtDireccion1.getText());
                    cli.setDireccion2(txtDireccion2.getText());
                    cli.setTelefono_casa(txtTelefonoCasa.getText());
                    cli.setTelefono_celular(txtTelefonoCelular.getText());
                    cli.setEmail(txtEmail.getText());
                    int edo = util.buscaIdEdo(Principal.estadosHM
                            , cboEstados.getSelectedItem().toString());
                    int mun = util.buscaIdMun(Principal.municipiosHM
                            , cboMunicipio.getSelectedItem().toString());
                    cli.setEstado("" + edo);
                    cli.setCiudad("" + mun);
                    cli.setCp(txtCP.getText());
                    cli.setPais("Mx");
                    cli.setNoCuenta(txtNoCuenta.getText());
                    cli.setComentarios(txtComentarios.getText());
                    //huardar producto
                    hiloClientes = new WSClientes();
                    String rutaWS = constantes.getProperty("IP") + constantes.getProperty("GUARDACLIENTE");
                    ClienteBean clienteInsertado = hiloClientes.ejecutaWebService(rutaWS,"1"
                            ,cli.getEmpresa()
                            ,cli.getNombre()
                            ,cli.getApellidos()
                            ,cli.getTelefono_casa()
                            ,cli.getTelefono_celular()
                            ,cli.getDireccion1()
                            ,cli.getDireccion2()
                            ,cli.getRfc()
                            ,cli.getEmail()
                            ,cli.getCiudad()
                            ,cli.getEstado()
                            ,cli.getCp()
                            ,cli.getPais()
                            ,cli.getComentarios()
                            ,cli.getNoCuenta()
                            );
                    if (clienteInsertado != null) {
                        JOptionPane.showMessageDialog(null, "[ Datos Agregados ]");
                        actualizarBusqueda();
                        limpiarCajatexto();
                        activarCajatexto(false);
                        activarBotones(true);
                    } else {
                        JOptionPane.showMessageDialog(null, 
                                "Error al guardar el registro");
                    }    
            } else {
                JOptionPane.showMessageDialog(null, 
                        "Llena los campos requeridos!!");
            }    
        }  
        if (accion.equalsIgnoreCase("Actualizar")) {
            if (txtNombreCli.getText().compareTo("") != 0 
                    && lblIdCliente.getText().compareTo("") != 0
                    && txtEmpresa.getText().compareTo("") != 0
                    && txtRFC.getText().compareTo("") != 0
                    && lblIdCliente.getText().compareTo("") != 0
                    && txtEmpresa.getText().compareTo("") != 0
                    && txtApellidos.getText().compareTo("") != 0
                    && txtDireccion1.getText().compareTo("") != 0
                    && txtTelefonoCasa.getText().compareTo("") != 0
                    && !cboEstados.getSelectedItem().toString().
                    equalsIgnoreCase("Seleccionar...")
                    && !cboMunicipio.getSelectedItem().toString().
                    equalsIgnoreCase("Seleccionar...")
                    && txtEmpresa.getText().compareTo("") != 0
                        ) {
                    ClienteBean cli = new ClienteBean();
                    cli.setIdCliente(Integer.parseInt(lblIdCliente.getText()));
                    cli.setEmpresa(txtEmpresa.getText());
                    cli.setRfc(txtRFC.getText());
                    cli.setNombre(txtNombreCli.getText());
                    cli.setApellidos(txtApellidos.getText());
                    cli.setDireccion1(txtDireccion1.getText());
                    cli.setDireccion2(txtDireccion2.getText());
                    cli.setTelefono_casa(txtTelefonoCasa.getText());
                    cli.setTelefono_celular(txtTelefonoCelular.getText());
                    cli.setEmail(txtEmail.getText());
                    int edo = util.buscaIdEdo(Principal.estadosHM
                            , cboEstados.getSelectedItem().toString());
                    int mun = util.buscaIdMun(Principal.municipiosHM
                            , cboMunicipio.getSelectedItem().toString());
                    cli.setEstado("" + edo);
                    cli.setCiudad("" + mun);
                    cli.setCp(txtCP.getText());
                    cli.setPais("Mx");
                    cli.setNoCuenta(txtNoCuenta.getText());
                    cli.setComentarios(txtComentarios.getText());
                    //huardar producto
                    hiloClientes = new WSClientes();
                    String rutaWS = constantes.getProperty("IP") + constantes.getProperty("MODIFICACLIENTE");
                    ClienteBean clienteActualizado = hiloClientes.ejecutaWebService(rutaWS,"2"
                            ,String.valueOf(cli.getIdCliente())
                            ,cli.getEmpresa()
                            ,cli.getNombre()
                            ,cli.getApellidos()
                            ,cli.getTelefono_casa()
                            ,cli.getTelefono_celular()
                            ,cli.getDireccion1()
                            ,cli.getDireccion2()
                            ,cli.getRfc()
                            ,cli.getEmail()
                            ,cli.getCiudad()
                            ,cli.getEstado()
                            ,cli.getCp()
                            ,cli.getPais()
                            ,cli.getComentarios()
                            ,cli.getNoCuenta()
                            );
                    if (clienteActualizado != null) {
                        JOptionPane.showMessageDialog(null, "[ Datos Actualizados ]");
                        actualizarBusqueda();
                        limpiarCajatexto();
                        activarCajatexto(false);
                        activarBotones(true);
                        jtCliente.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(null, 
                                "Error al actualizar el registro");
                    }    
            } else {
                JOptionPane.showMessageDialog(null, 
                        "Llena los campos requeridos!!");
            }    
        }  
        btnNuevoCli.setEnabled(true);
    }//GEN-LAST:event_btnGuardarCliActionPerformed

    private void btnModificarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarCliActionPerformed
        if (lblIdCliente.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar un registro");
            return;
        }
        jtCliente.setEnabled(false);
        accion = "Actualizar";
        activarCajatexto(true);
        btnNuevoCli.setEnabled(false);
        btnGuardarCli.setEnabled(true);
//        btnModificarCli.setEnabled(false);
//        btnCancelarCli.setEnabled(true);
//        btnMostrarCli.setEnabled(false);
    }//GEN-LAST:event_btnModificarCliActionPerformed

    private void txtApellidosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidosKeyTyped
        // TODO add your handling code here:
//        if (String.valueOf(evt.getKeyChar()).matches("[a-zA-Z]|\\s")) {
//            Toolkit.getDefaultToolkit().beep();
//            evt.consume();
//        }
    }//GEN-LAST:event_txtApellidosKeyTyped

    private void txtRFCKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRFCKeyTyped
//        if (String.valueOf(evt.getKeyChar()).matches("[a-zA-Z]|\\s")) {
//            Toolkit.getDefaultToolkit().beep();
//            evt.consume();
//        }
    }//GEN-LAST:event_txtRFCKeyTyped

    private void txtTelefonoCasaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoCasaKeyTyped
        if (String.valueOf(evt.getKeyChar()).matches("[a-zA-Z]|\\s")) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtTelefonoCasaKeyTyped

    private void buscaClienteFromJTable() {
        lblIdCliente.setText(jtCliente.getModel().getValueAt(
            jtCliente.getSelectedRow(),0).toString());
        ArrayList<ClienteBean> resultWS = null;
        hiloClientesList = new WSClientesList();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty("GETCLIENTEPORID")
                + String.valueOf(jtCliente.getModel().getValueAt(jtCliente.getSelectedRow(), 0)).trim();
        resultWS = hiloClientesList.ejecutaWebService(rutaWS,"2");
        ClienteBean cli = resultWS.get(0);
        txtEmpresa.setText(cli.getEmpresa());
        txtApellidos.setText(cli.getApellidos());
        txtRFC.setText(cli.getRfc());
        txtNombreCli.setText(cli.getNombre());
        txtDireccion1.setText(cli.getDireccion1());
        txtDireccion2.setText(cli.getDireccion2());
        txtTelefonoCasa.setText(cli.getTelefono_casa());
        txtTelefonoCelular.setText(cli.getTelefono_celular());
        txtEmail.setText(cli.getEmail());
        txtCP.setText(cli.getCp());
        txtNoCuenta.setText(cli.getNoCuenta());
        txtComentarios.setText(cli.getComentarios());
        String edo = "";
        String munic = "";
        edo = util.buscaDescFromIdEdo(Principal.estadosHM, 
                cli.getEstado().toString());
        if ("".equalsIgnoreCase(edo)) {
            cboEstados.setSelectedItem("Seleccionar...");
        } else {
            cboEstados.setSelectedItem(edo);
        }
        munic = util.buscaDescFromIdMun(Principal.municipiosHM, 
                cli.getCiudad());
        if ("".equalsIgnoreCase(munic)) {
            cboMunicipio.setSelectedItem("Seleccionar...");
        } else {
            cboMunicipio.setSelectedItem(munic);
        }
        jtCliente.requestFocus(true);
    }
    
    private void jtClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtClienteMouseClicked
        buscaClienteFromJTable();
    }//GEN-LAST:event_jtClienteMouseClicked

    private void jtClienteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtClienteMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jtClienteMouseEntered

    private void eliminarCliente() {
        int dialogResult = JOptionPane.showConfirmDialog(null, "¿Realmente deseas borrar el registro?");
        if(dialogResult == JOptionPane.YES_OPTION){
            if (lblIdCliente.getText().compareTo("") != 0) {
                hiloClientes = new WSClientes();
                String rutaWS = constantes.getProperty("IP") + constantes.getProperty("ELIMINACLIENTE");
                ClienteBean clienteEliminar = hiloClientes.ejecutaWebService(rutaWS,"3"
                        ,lblIdCliente.getText().trim());
                if (clienteEliminar != null) {
                    JOptionPane.showMessageDialog(null, " [ Registro Eliminado ]");
                    //Carga productos
                    actualizarBusqueda();
                    limpiarCajatexto();
                    activarCajatexto(false);
                    activarBotones(true);
                } else {
                    JOptionPane optionPane = new JOptionPane("No es posible "
                            + "eliminar el "
                            + "cliente existen movimientos que lo relacionan"
                            , JOptionPane.ERROR_MESSAGE);    
                    JDialog dialog = optionPane.createDialog("Error");
                    dialog.setAlwaysOnTop(true);
                    dialog.setVisible(true);                    
                }
            } else {
                JOptionPane.showMessageDialog(null, 
                        "No hay cliente seleccionado");
            }
            btnCancelarCli.setEnabled(true);
        }
    }
    
    private void btnEliminarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCliActionPerformed
        eliminarCliente();
    }//GEN-LAST:event_btnEliminarCliActionPerformed

    private void txtTelefonoCelularKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoCelularKeyTyped
        if (String.valueOf(evt.getKeyChar()).matches("[a-zA-Z]|\\s")) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtTelefonoCelularKeyTyped

    private void txtRFCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRFCActionPerformed
        txtNombreCli.requestFocus();
    }//GEN-LAST:event_txtRFCActionPerformed

    private void txtApellidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidosActionPerformed
        txtDireccion1.requestFocus();
    }//GEN-LAST:event_txtApellidosActionPerformed

    private void txtNombreCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreCliActionPerformed
        txtApellidos.requestFocus();
    }//GEN-LAST:event_txtNombreCliActionPerformed

    private void txtDireccion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccion1ActionPerformed
        txtDireccion2.requestFocus();
    }//GEN-LAST:event_txtDireccion1ActionPerformed

    private void txtTelefonoCasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoCasaActionPerformed
        txtTelefonoCelular.requestFocus();
    }//GEN-LAST:event_txtTelefonoCasaActionPerformed

    private void txtTelefonoCelularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoCelularActionPerformed
        txtEmail.requestFocus();
    }//GEN-LAST:event_txtTelefonoCelularActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        cboEstados.requestFocus();
    }//GEN-LAST:event_txtEmailActionPerformed

    private void cboEstadosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboEstadosKeyTyped
//        int key=evt.getKeyCode();
//        if(key==0)
//        {
//            String item = cboEstados.getSelectedItem().toString();
//            JOptionPane.showMessageDialog(null, item);
//        }
    }//GEN-LAST:event_cboEstadosKeyTyped

    private void cboEstadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEstadosActionPerformed
        String item = cboEstados.getSelectedItem().toString();
        int indiceEdo = util.buscaIdEdo(Principal.estadosHM, item);
        cboMunicipio.removeAllItems();
        List<String> listMuni = new ArrayList();
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        modelo.addElement("Seleccionar...");
        // llena combo con municipios
        for (EdoMunBean s : Principal.estadosMun) {
          if (s.getIdEstado() == indiceEdo) {
            String muni = util.buscaDescFromIdMun(Principal.municipiosHM, "" 
                    + s.getIdMunicipio());
            listMuni.add(muni);
          }
        }
        Collections.sort(listMuni);
        //Collections.reverse(listMuni);
        for (String listMuni1 : listMuni) {
            modelo.addElement(listMuni1);
        }
        cboMunicipio.setModel(modelo);
        // llena combo con municipios
        cboMunicipio.requestFocus();
    }//GEN-LAST:event_cboEstadosActionPerformed

    private void jtClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtClienteKeyReleased
        if (evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP) {
             buscaClienteFromJTable();
        }
    }//GEN-LAST:event_jtClienteKeyReleased

    private void txtEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmpresaActionPerformed
        txtRFC.requestFocus(true);
    }//GEN-LAST:event_txtEmpresaActionPerformed

    private void txtDireccion2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccion2ActionPerformed
        txtTelefonoCasa.requestFocus();
    }//GEN-LAST:event_txtDireccion2ActionPerformed

    private void cboMunicipioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMunicipioActionPerformed
        txtCP.requestFocus(true);
    }//GEN-LAST:event_cboMunicipioActionPerformed

    private void txtCPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCPActionPerformed
        txtNoCuenta.requestFocus();
    }//GEN-LAST:event_txtCPActionPerformed

    private void txtNoCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoCuentaActionPerformed
        txtComentarios.requestFocus();
    }//GEN-LAST:event_txtNoCuentaActionPerformed

    private void txtComentariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtComentariosActionPerformed
        btnGuardarCli.requestFocus();
    }//GEN-LAST:event_txtComentariosActionPerformed

    private void actualizarBusqueda() {
        ArrayList<ClienteBean> resultWS = null;
        String rutaWS = "";
        if (String.valueOf(cboParametroCli.getSelectedItem()).
                equalsIgnoreCase("Nombre")) {
            if (txtBuscarCli.getText().equalsIgnoreCase("")) {
                hiloClientesList = new WSClientesList();
                rutaWS = constantes.getProperty("IP") + constantes.
                        getProperty("GETCLIENTES");
                resultWS = hiloClientesList.ejecutaWebService(rutaWS,"1");
            } else {
                hiloClientesList = new WSClientesList();
                rutaWS = constantes.getProperty("IP") + constantes.
                        getProperty("GETCLIENTEBUSQUEDANOMBRE")
                    + txtBuscarCli.getText().trim();
                resultWS = hiloClientesList.ejecutaWebService(rutaWS,"4");
            }
        } else {
            if (String.valueOf(cboParametroCli.getSelectedItem()).
                    equalsIgnoreCase("Id")) {
                if (txtBuscarCli.getText().equalsIgnoreCase("")) {
                    hiloClientesList = new WSClientesList();
                    rutaWS = constantes.getProperty("IP") 
                            + constantes.getProperty("GETCLIENTES");
                    resultWS = hiloClientesList.ejecutaWebService(rutaWS,"1");
                } else {
                    hiloClientesList = new WSClientesList();
                    rutaWS = constantes.getProperty("IP") 
                            + constantes.getProperty("GETCLIENTEBUSQUEDAID") 
                            + txtBuscarCli.getText().trim();
                    resultWS = hiloClientesList.ejecutaWebService(rutaWS,"3");
                }
            }
        }        
        recargarTable(resultWS);
    }

    public void recargarTable(ArrayList<ClienteBean> list) {
        Object[][] datos = new Object[list.size()][2];
        int i = 0;
        for (ClienteBean p : list) {
            datos[i][0] = p.getIdCliente();
            datos[i][1] = p.getNombre() + " " + p.getApellidos();
            i++;
        }
        jtCliente.setModel(new javax.swing.table.DefaultTableModel(
                datos,
                new String[]{
                    "ID CLIENTE", "NOMBRE"
                }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelarCli;
    private javax.swing.JButton btnEliminarCli;
    private javax.swing.JButton btnGuardarCli;
    private javax.swing.JButton btnModificarCli;
    private javax.swing.JButton btnMostrarCli;
    private javax.swing.JButton btnNuevoCli;
    private javax.swing.JButton btnSalirCli;
    private javax.swing.JComboBox cboEstados;
    private javax.swing.JComboBox cboMunicipio;
    private javax.swing.JComboBox cboParametroCli;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jtCliente;
    private javax.swing.JLabel lblIdCliente;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtBuscarCli;
    private javax.swing.JTextField txtCP;
    private javax.swing.JTextField txtComentarios;
    private javax.swing.JTextField txtDireccion1;
    private javax.swing.JTextField txtDireccion2;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEmpresa;
    private javax.swing.JTextField txtNoCuenta;
    private javax.swing.JTextField txtNombreCli;
    private javax.swing.JTextField txtRFC;
    private javax.swing.JTextField txtTelefonoCasa;
    private javax.swing.JTextField txtTelefonoCelular;
    // End of variables declaration//GEN-END:variables
}