# Peya Ecommerce App 🛒

Aplicación de ecommerce desarrollada con Jetpack Compose, arquitectura MVVM, persistencia local con Room, y conectividad con API REST mediante Retrofit.

## - Funcionalidades

- **Login**: Validación de email y contraseña.
- **Registro**: Alta de usuarios con validaciones y confirmación de contraseña.
- **Listado de Productos**: Muestra dinámica de productos disponibles.
- **Filtros para vista de Productos**: Filtrado por categoría (Bebida, Comida, Postre).
- **Búsqueda de Productos**: Búsqueda dinámica en la lista.
- **Carrito de Compras**: Agregar, editar cantidad, eliminar productos y calcular total.
- **Persistencia del Carrito**: Guarda los productos incluso al cerrar la app (Room).
- **Historial de Órdenes**: Muestra las compras realizadas y se actualiza correctamente.

## - Conectividad y Servicios

- Los datos se obtienen desde una **API REST** usando **Retrofit**.
- `ApiService` configurado con todos los endpoints necesarios.
- Uso de **loaders** durante las operaciones de red.
- **Hilt** para inyección de dependencias (ViewModels, repositorios, servicios).

## - Persistencia y Sincronización

- **Room**: El carrito y las órdenes se almacenan localmente.
- La base de datos incluye **versionado y migraciones** en caso de cambios.

## - Arquitectura y Testing

- Implementación completa del patrón **MVVM**.
- Organización en capas: `UI`, `ViewModel`, `data`, `domain`.
- Gestión de estado **reactivo y asincrónico** en los ViewModels.
- Pruebas unitarias en ViewModels y repositorios con **mocks**.

## - Interfaz de Usuario

- Toda la UI está desarrollada con **Jetpack Compose**.
- Interfaz clara, consistente y sin errores visuales importantes.

## - Calidad y Entrega

- Código limpio, sin **logs de depuración** ni **valores hardcodeados**.
- Navegación funcional entre pantallas.
- Repositorio actualizado y bien organizado con **commits claros**.

