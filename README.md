# Sistema de Alerta de Intrusión para Bodega

Este es un proyecto de Android que simula un sistema de **alerta de intrusión cliente-servidor**.  
La aplicación puede funcionar en dos modos: como un **Cliente (Sensor)** que envía alertas y como un **Dashboard (Receptor)** que las recibe y muestra.

---

## 🚀 Características

- **Arquitectura Cliente-Servidor:** La comunicación se realiza a través de *Sockets* en una red local (Wi-Fi).  
- **Doble Rol:** Una sola aplicación que puede actuar como cliente o como dashboard, seleccionable al inicio.  
- **Simulación de Alerta:** El cliente puede enviar una señal de “detección de movimiento” al dashboard.  
- **Notificación de Alarma:** El dashboard muestra una alerta visual llamativa al recibir una señal.  
- **Registro de Eventos (Log):** El dashboard guarda un historial de todas las alertas recibidas, incluyendo:  
  - Nombre del dispositivo emisor.  
  - Dirección IP del emisor.  
  - Fecha y hora de la alerta.  
- **Interfaz Personalizada:** Diseño temático en colores rojo, blanco y negro.

---

## 🛠️ Tecnologías Utilizadas

- **Kotlin**  
- **Jetpack Compose** para la interfaz de usuario  
- **Navigation Compose** para la navegación entre pantallas  
- **Sockets de Java** para la comunicación en red

---

## 📋 Cómo Usarlo

Para probar la funcionalidad completa, necesitas instalar la aplicación en **dos dispositivos** (por ejemplo, un teléfono físico y un emulador) conectados a la **misma red Wi-Fi**.

### 🔹 Paso 1: Configurar el Dashboard (Receptor)

Este dispositivo actuará como el servidor que escucha las alertas.  
Se recomienda usar un teléfono físico.

1. Conecta tu teléfono a la red Wi-Fi.  
2. Encuentra la dirección IP de tu teléfono:  
   - Ve a **Ajustes > Wi-Fi**  
   - Toca en la red a la que estás conectado  
   - Busca la **Dirección IP** y anótala (ej: `192.168.1.12`)  
3. Instala y ejecuta la aplicación en el teléfono.  
4. En la pantalla de inicio, selecciona **"Actuar como Dashboard (Receptor)"**.  
5. La pantalla mostrará **"Esperando alertas..."**. está listo para recibir la señal 

---

### 🔹 Paso 2: Configurar el Cliente (Sensor)

Este dispositivo enviará la alerta. Puedes usar un **emulador de Android Studio** o un **segundo dispositivo fisico**.

1. Asegúrate de que el emulador tenga conexión a internet.  
2. Instala y ejecuta la aplicación en el emulador.  
3. En la pantalla de inicio, selecciona **"Actuar como Cliente (Sensor)"**.  
4. En el campo de texto **"IP del Servidor"**, introduce la dirección IP de tu teléfono físico que anotaste en el paso anterior.  
5. Presiona el botón rojo **"Simular Detección de Movimiento"**.

---

## Resultado

Al instante, la pantalla de tu teléfono físico (el Dashboard) cambiará para mostrar:

> **¡ALARMA ACTIVADA!**

Además, se añadirá una nueva entrada al registro en la parte inferior de la pantalla con los datos del evento.

---
