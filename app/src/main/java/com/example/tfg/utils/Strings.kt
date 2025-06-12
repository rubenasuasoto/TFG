package com.example.tfg.utils



enum class Idioma {
    ESP, ENG
}

object Strings {
    var idioma: Idioma = Idioma.ESP

    val loginTitulo: String
        get() = when (idioma) {
            Idioma.ESP -> "Iniciar Sesión"
            Idioma.ENG -> "Sign In"
        }

    val loginBoton: String
        get() = when (idioma) {
            Idioma.ESP -> "Entrar"
            Idioma.ENG -> "Login"
        }

    val registroTexto: String
        get() = when (idioma) {
            Idioma.ESP -> "¿No tienes cuenta? Regístrate aquí"
            Idioma.ENG -> "Don't have an account? Sign up here"
        }

    val ajustes: String
        get() = when (idioma) {
            Idioma.ESP -> "Ajustes"
            Idioma.ENG -> "Settings"
        }

    val temaOscuro: String
        get() = when (idioma) {
            Idioma.ESP -> "Modo oscuro"
            Idioma.ENG -> "Dark mode"
        }

    val tamañoLetra: String
        get() = when (idioma) {
            Idioma.ESP -> "Tamaño de letra"
            Idioma.ENG -> "Font size"
        }

    val idiomaLabel: String
        get() = when (idioma) {
            Idioma.ESP -> "Idioma"
            Idioma.ENG -> "Language"
        }

    val carrito: String
        get() = when (idioma) {
            Idioma.ESP -> "Carrito"
            Idioma.ENG -> "Cart"
        }

    val menu: String
        get() = when (idioma) {
            Idioma.ESP -> "Menú"
            Idioma.ENG -> "Menu"
        }
    val loginUsuario: String
        get() = when (idioma) {
            Idioma.ESP -> "Usuario"
            Idioma.ENG -> "Username"
        }

    val loginPassword: String
        get() = when (idioma) {
            Idioma.ESP -> "Contraseña"
            Idioma.ENG -> "Password"
        }

    val loginErrorUsuario: String
        get() = when (idioma) {
            Idioma.ESP -> "El nombre de usuario no puede estar vacío"
            Idioma.ENG -> "Username cannot be empty"
        }

    val loginErrorPassword: String
        get() = when (idioma) {
            Idioma.ESP -> "La contraseña no puede estar vacía"
            Idioma.ENG -> "Password cannot be empty"
        }

    val loginNoCuenta: String
        get() = when (idioma) {
            Idioma.ESP -> "¿No tienes cuenta? Regístrate aquí"
            Idioma.ENG -> "Don't have an account? Sign up here"
        }
    val registroTitulo get() = if (idioma == Idioma.ESP) "Registro" else "Register"
    val registroUsuario get() = if (idioma == Idioma.ESP) "Usuario" else "Username"
    val registroEmail get() = if (idioma == Idioma.ESP) "Email" else "Email"
    val registroPassword get() = if (idioma == Idioma.ESP) "Contraseña" else "Password"
    val registroRepetirPassword get() = if (idioma == Idioma.ESP) "Repetir contraseña" else "Repeat password"
    val registroErrorPassword get() = if (idioma == Idioma.ESP) "Contraseña débil. Mínimo 8 caracteres, 1 número y 1 símbolo" else "Weak password. Min. 8 characters, 1 number, 1 symbol"
    val registroErrorCoinciden get() = if (idioma == Idioma.ESP) "Las contraseñas no coinciden" else "Passwords do not match"
    val registroBoton get() = if (idioma == Idioma.ESP) "Registrarse" else "Register"
    val registroTieneCuenta get() = if (idioma == Idioma.ESP) "¿Ya tienes cuenta? Inicia sesión" else "Already have an account? Log in"
    val registroCalle get() = if (idioma == Idioma.ESP) "Calle" else "Street"
    val registroNumero get() = if (idioma == Idioma.ESP) "Número" else "Number"
    val registroMunicipio get() = if (idioma == Idioma.ESP) "Municipio" else "Town/City"
    val registroProvincia get() = if (idioma == Idioma.ESP) "Provincia" else "Province"
    val registroCP get() = if (idioma == Idioma.ESP) "Código Postal" else "Postal Code"
    val registroCamposObligatorios get() = if (idioma == Idioma.ESP) "❌ Todos los campos son obligatorios." else "❌ All fields are required."
    val registroErrores get() = if (idioma == Idioma.ESP) "❌ Corrige los errores antes de continuar." else "❌ Fix the errors before proceeding."

    val homeRecomendados get() = if (idioma == Idioma.ESP) "Recomendados" else "Recommended"
    val homeProductos get() = if (idioma == Idioma.ESP) "Productos" else "Products"
    val homeNecesitaLogin get() = if (idioma == Idioma.ESP) "Necesitas iniciar sesión" else "You need to log in"
    val homeAccionIniciarSesion get() = if (idioma == Idioma.ESP) "Iniciar sesión" else "Log in"

    val carritoTitulo get() = if (idioma == Idioma.ESP) "Carrito" else "Cart"
    val carritoVacio get() = if (idioma == Idioma.ESP) "🛒 Tu carrito está vacío" else "🛒 Your cart is empty"
    val carritoEliminar get() = if (idioma == Idioma.ESP) "Eliminar del carrito" else "Remove from cart"
    val carritoTotal get() = if (idioma == Idioma.ESP) "Total" else "Total"
    val carritoFinalizar get() = if (idioma == Idioma.ESP) "Finalizar compra" else "Checkout"
    val carritoSeguir get() = if (idioma == Idioma.ESP) "Seguir comprando" else "Keep shopping"
    val carritoProcesando get() = if (idioma == Idioma.ESP) "Procesando compra..." else "Processing order..."

    val bottomCarrito get() = if (idioma == Idioma.ESP) "Carrito" else "Cart"
    val bottomInicio get() = if (idioma == Idioma.ESP) "Inicio" else "Home"
    val bottomMenu get() = if (idioma == Idioma.ESP) "Menú" else "Menu"
    val buscarProductoLabel get() = if (idioma == Idioma.ESP) "Buscar producto" else "Search product"
    val noResultados get() = if (idioma == Idioma.ESP) "No se encontraron productos" else "No results found"
    val sinNombre get() = if (idioma == Idioma.ESP) "Sin nombre" else "Unnamed"

    val precioLabel get() = if (idioma == Idioma.ESP) "Precio:" else "Price:"
    val stockDisponibleLabel get() = if (idioma == Idioma.ESP) "Stock disponible:" else "Available stock:"
    val agregarAlCarrito get() = if (idioma == Idioma.ESP) "Agregar al carrito" else "Add to cart"
    val sinStock get() = if (idioma == Idioma.ESP) "Sin stock" else "Out of stock"
    val debesIniciarSesion get() = if (idioma == Idioma.ESP) "Debes iniciar sesión" else "You must log in"
    val iniciarSesion get() = if (idioma == Idioma.ESP) "Iniciar sesión" else "Log in"

    val adminDashboardTitulo get() = if (idioma == Idioma.ESP) "Dashboard Admin" else "Admin Dashboard"
    val adminDashboardProductos get() = if (idioma == Idioma.ESP) "Productos registrados" else "Registered Products"
    val adminDashboardUsuarios get() = if (idioma == Idioma.ESP) "Usuarios registrados" else "Registered Users"
    val adminDashboardPedidosTotales get() = if (idioma == Idioma.ESP) "Pedidos totales" else "Total Orders"
    val adminDashboardPedidosPendientes get() = if (idioma == Idioma.ESP) "Pendientes" else "Pending"
    val adminDashboardPedidosCompletados get() = if (idioma == Idioma.ESP) "Entregados" else "Delivered"
    val adminDashboardVentasTotales get() = if (idioma == Idioma.ESP) "Ventas totales (€)" else "Total Sales (€)"
    val volver get() = if (idioma == Idioma.ESP) "Volver" else "Back"

    val adminPedidosTitulo get() = if (idioma == Idioma.ESP) "Gestión de pedidos (admin)" else "Order Management (admin)"
    val adminPedidosBuscarLabel get() = if (idioma == Idioma.ESP) "Buscar por usuario, estado o producto" else "Search by user, status or product"
    val adminPedidosNoResultados get() = if (idioma == Idioma.ESP) "No se encontraron pedidos." else "No orders found."
    val pedidoNumero get() = if (idioma == Idioma.ESP) "Pedido Nº" else "Order No."
    val usuario get() = if (idioma == Idioma.ESP) "Usuario" else "User"
    val productos get() = if (idioma == Idioma.ESP) "Productos:" else "Products:"
    val estado get() = if (idioma == Idioma.ESP) "Estado" else "Status"
    val total get() = if (idioma == Idioma.ESP) "Total" else "Total"
    val fecha get() = if (idioma == Idioma.ESP) "Fecha" else "Date"
    val eliminar get() = if (idioma == Idioma.ESP) "Eliminar" else "Delete"

    val adminProductosTitulo get() = if (idioma == Idioma.ESP) "Gestión de productos" else "Product Management"
    val nuevo get() = if (idioma == Idioma.ESP) "Nuevo producto" else "New product"
    val buscarProducto get() = if (idioma == Idioma.ESP) "Buscar producto" else "Search product"
    val editar get() = if (idioma == Idioma.ESP) "Editar" else "Edit"
    val formularioProducto get() = if (idioma == Idioma.ESP) "Formulario de producto" else "Product Form"
    val nombreProducto get() = if (idioma == Idioma.ESP) "Nombre del producto" else "Product name"
    val campoObligatorio get() = if (idioma == Idioma.ESP) "Este campo es obligatorio" else "This field is required"
    val precioInvalido get() = if (idioma == Idioma.ESP) "Precio inválido (debe ser mayor que 0)" else "Invalid price (must be > 0)"
    val stockInvalido get() = if (idioma == Idioma.ESP) "Stock inválido (mínimo 0)" else "Invalid stock (min 0)"
    val urlImagen get() = if (idioma == Idioma.ESP) "URL de la imagen" else "Image URL"
    val guardar get() = if (idioma == Idioma.ESP) "Guardar" else "Save"
    val cancelar get() = if (idioma == Idioma.ESP) "Cancelar" else "Cancel"


    val adminUsuariosTitulo get() = if (idioma == Idioma.ESP) "Gestión de usuarios" else "User Management"
    val buscarUsuario get() = if (idioma == Idioma.ESP) "Buscar por nombre o email" else "Search by name or email"
    val usuariosNoEncontrados get() = if (idioma == Idioma.ESP) "No se encontraron usuarios." else "No users found."
    val email get() = if (idioma == Idioma.ESP) "Email" else "Email"
    val rol get() = if (idioma == Idioma.ESP) "Rol" else "Role"
    val precio get() = if (idioma == Idioma.ESP) "Precio" else "Price"
    val stock get() = if (idioma == Idioma.ESP) "Stock" else "Stock"

    val cambiarPasswordTitulo get() = if (idioma == Idioma.ESP) "Cambiar contraseña" else "Change password"
    val passwordActual get() = if (idioma == Idioma.ESP) "Contraseña actual" else "Current password"
    val nuevaPassword get() = if (idioma == Idioma.ESP) "Nueva contraseña" else "New password"
    val repetirPassword get() = if (idioma == Idioma.ESP) "Repetir nueva contraseña" else "Repeat new password"
    val passwordInvalida get() = if (idioma == Idioma.ESP) "Debe tener al menos 8 caracteres, incluir un número y un carácter especial" else "Must be at least 8 characters long, include a number and a special character"
    val passwordNoCoincide get() = if (idioma == Idioma.ESP) "Las contraseñas no coinciden" else "Passwords do not match"
    val cambiarPasswordBoton get() = if (idioma == Idioma.ESP) "Cambiar contraseña" else "Change password"


    val menuOpciones get() = if (idioma == Idioma.ESP) "Menú de Opciones" else "Options Menu"
    val perfil get() = if (idioma == Idioma.ESP) "Perfil" else "Profile"
    val pedidos get() = if (idioma == Idioma.ESP) "Pedidos" else "Orders"
    val cerrarSesion get() = if (idioma == Idioma.ESP) "Cerrar sesión" else "Log out"

    val adminGestionProductos get() = if (idioma == Idioma.ESP) "Gestión de productos" else "Manage Products"
    val adminGestionPedidos get() = if (idioma == Idioma.ESP) "Gestión de pedidos" else "Manage Orders"
    val adminGestionUsuarios get() = if (idioma == Idioma.ESP) "Gestión de usuarios" else "Manage Users"
    val adminDashboard get() = if (idioma == Idioma.ESP) "Dashboard" else "Dashboard"
    val adminPersonalizacion get() = if (idioma == Idioma.ESP) "Personalización" else "Customization"
    val estadoEntregado get() = if (idioma == Idioma.ESP) "Entregado" else "Delivered"
    val estadoPendiente get() = if (idioma == Idioma.ESP) "Pendiente" else "Pending"
    val estadoCancelado get() = if (idioma == Idioma.ESP) "Cancelado" else "Cancelled"

    val tituloMisPedidos get() = if (idioma == Idioma.ESP) "Mis pedidos" else "My Orders"
    val verFactura get() = if (idioma == Idioma.ESP) "Ver factura" else "View Invoice"
    val cancelarPedido get() = if (idioma == Idioma.ESP) "Cancelar pedido" else "Cancel Order"
    val cerrar get() = if (idioma == Idioma.ESP) "Cerrar" else "Close"
    val factura get() = if (idioma == Idioma.ESP) "Factura" else "Invoice"
    val numeroFactura get() = if (idioma == Idioma.ESP) "Nº Factura" else "Invoice Nº"
    val pedidosVacio get() = if (idioma == Idioma.ESP) "No tienes pedidos aún." else "You don't have any orders yet."
    val perfilTitulo get() = if (idioma == Idioma.ESP) "Mi perfil" else "My profile"
    val perfilUsuario get() = if (idioma == Idioma.ESP) "Nombre de usuario" else "Username"
    val perfilEmail get() = if (idioma == Idioma.ESP) "Email" else "Email"
    val perfilDireccion get() = if (idioma == Idioma.ESP) "Dirección" else "Address"
    val direccionCalle get() = if (idioma == Idioma.ESP) "Calle" else "Street"
    val direccionMunicipio get() = if (idioma == Idioma.ESP) "Municipio" else "City"
    val direccionProvincia get() = if (idioma == Idioma.ESP) "Provincia" else "Province"
    val direccionCP get() = if (idioma == Idioma.ESP) "Código Postal" else "Postal Code"
    val errorEmail get() = if (idioma == Idioma.ESP) "Email inválido" else "Invalid email"
    val errorProvinciaVacia get() = if (idioma == Idioma.ESP) "La provincia no puede estar vacía" else "Province cannot be empty"
    val errorCamposInvalidos get() = if (idioma == Idioma.ESP) "❌ Corrige los errores antes de guardar" else "❌ Fix the errors before saving"

    val personalizacionTitulo get() = if (idioma == Idioma.ESP) "Personalización de la App" else "App Customization"
    val coloresActuales get() = if (idioma == Idioma.ESP) "Colores actuales:" else "Current colors:"
    val modoClaro get() = if (idioma == Idioma.ESP) "Claro" else "Light"
    val modoOscuro get() = if (idioma == Idioma.ESP) "Oscuro" else "Dark"
    val seleccionarColorClaro get() = if (idioma == Idioma.ESP) "Seleccionar color para Modo CLARO:" else "Select color for LIGHT mode:"
    val seleccionarColorOscuro get() = if (idioma == Idioma.ESP) "Seleccionar color para Modo OSCURO:" else "Select color for DARK mode:"
    val avisoGlobal get() = if (idioma == Idioma.ESP) "Los colores personalizados afectan globalmente la app para todos los usuarios." else "Custom colors affect the app globally for all users."

    val ajustesTitulo get() = if (idioma == Idioma.ESP) "Ajustes" else "Settings"
    val tamanoLetra get() = if (idioma == Idioma.ESP) "Tamaño de letra" else "Font size"
    val pequeno get() = if (idioma == Idioma.ESP) "Pequeño" else "Small"
    val medio get() = if (idioma == Idioma.ESP) "Medio" else "Medium"
    val grande get() = if (idioma == Idioma.ESP) "Grande" else "Large"


    val cambiarEstado get() = if (idioma == Idioma.ESP) "Cambiar estado" else "Change status"


    fun estadoLegible(estado: String): String {
        return when (estado.uppercase()) {
            "PENDIENTE" -> if (idioma == Idioma.ESP) "Pendiente" else "Pending"
            "COMPLETADO" -> if (idioma == Idioma.ESP) "Entregado" else "Delivered"
            "CANCELADO" -> if (idioma == Idioma.ESP) "Cancelado" else "Canceled"
            else -> estado
        }
    }





    // Agrega más textos aquí según necesidades
}
