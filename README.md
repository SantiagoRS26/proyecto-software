# Despliegue local del proyecto con Camunda

Este documento describe los pasos necesarios para desplegar el proyecto Java utilizando Camunda BPM localmente.

## Prerrequisitos

Asegúrate de tener instalado Intellij, Git, Node.js y JDK 17  antes de comenzar.

## Configuración del entorno

### 1. Clonar el repositorio

Primero, necesitarás clonar el código fuente del proyecto desde GitHub. Abre una terminal y ejecuta el siguiente comando:

```bash
git clone https://github.com/SantiagoRS26/Poliza-Seguro-Camunda.git
```

### 2. Instalar JDK 17

Descarga e instala JDK 17 desde el sitio web de Oracle. Puedes encontrar el enlace de descarga aquí:

- [Oracle JDK 17](https://www.oracle.com/java/technologies/downloads/#jdk17-windows)

### 3. Configurar la variable de entorno JAVA_HOME

Configura la variable `JAVA_HOME` para apuntar a la carpeta donde se instaló el JDK 17. La forma de configurar esta variable puede variar según tu sistema operativo.

#### Para Windows:

- Ve a Control Panel > System and Security > System > Advanced system settings > Environment Variables.
- Bajo System Variables, haz clic en New y añade `JAVA_HOME` con el path al JDK.

![Variables de Entorno](/images_md/JAVA_HOME.jpg)


### 4. Ejecutar el proyecto

Si estás utilizando IntelliJ IDEA, puedes ejecutar el proyecto de la siguiente manera:

1. Abre IntelliJ IDEA.
2. Abre la carpeta del proyecto donde se encuentra el archivo `pom.xml`.
3. Espera a que IntelliJ importe el proyecto y configure las dependencias.
4. Busca la clase principal del proyecto (por lo general, esta clase tendrá la anotación `@SpringBootApplication`).
5. Haz clic derecho en la clase principal y selecciona "Run <Nombre de la Clase Principal>".
6. IntelliJ iniciará la aplicación junto con el motor de Camunda BPM.


### 5. Verificar el despliegue

Una vez que la aplicación esté corriendo, puedes acceder a la interfaz de Camunda en tu navegador a través de:

```
http://localhost:8080/camunda-welcome/index.html
```

## Ejecutar el proceso de negocio

Una vez que la aplicación esté en funcionamiento, sigue estos pasos para ejecutar el proceso de negocio utilizando Camunda Modeler:

1. Estando en la siguiente ruta:

    ```
    Parcial2Software\Parcial2Software\workers\
    ```

    Ejecutar el siguiente comando:

    ```
    node .\cancelInsurence.js
    ```

2. Abre Camunda Modeler en tu sistema y carga el archivo BPMN del proceso de negocio ubicado en la siguiente ruta: 
    ```
    Parcial2Software\Parcial2Software\src\main\resources\META-INF\static.bpmn\Poliza Vehiculo (1).bpmn
    ```

3. Despliega el proceso a traves de Camunda Modeler:

![alt text](/images_md/image.png)


4. Se ingresa a la plataforma para acceder al menu de tasklist.
    ```
    http://localhost:8080/camunda/app/tasklist/default/#
    ```
    Teniendo en cuenta que las credenciales de acceso son las siguientes:

    user: `demo`

    password: `demo`


5. Luego de ingresar a la plataforma se inicia una instancia del proceso desde la interfaz de usuario de Camunda.

![alt text](/images_md/Camunda.jpg)

## Soporte

Si encuentras algún problema durante el despliegue, por favor abre un issue en el repositorio de GitHub o contacta al equipo de soporte.