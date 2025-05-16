<h1>Arquitectura Api-Portal</h1>

<pre>
Api-Portal/
├── <span style="color: #4CAF50;">deploy/</span>
│   ├── api-portal-1.0.0.jar
│   ├── docker-compose.yml
│   ├── Dockerfile
│   └── rebuild.sh
├── <span style="color: #2196F3;">src/</span>
│   └── <span style="color: #2196F3;">main/</span>
│       ├── <span style="color: #2196F3;">java/</span>
│       │   └── <span style="color: #2196F3;">py.com.nsa.microservice.api.portal/</span>
│       │       └── <span style="color: #2196F3;">components/</span>
│       │           ├── <span style="color: #FF9800;">acceso/</span>
│       │           ├── <span style="color: #FF9800;">calendar/</span>
│       │           ├── <span style="color: #FF9800;">manual/</span>
│       │           ├── <span style="color: #FF9800;">manualdetalle/</span>
│       │           ├── <span style="color: #FF9800;">menu/</span>
│       │           ├── <span style="color: #FF9800;">sessionlog/</span>
│       │           ├── <span style="color: #FF9800;">slider/</span>
│       │           ├── <span style="color: #FF9800;">tarea/</span>
│       │           ├── <span style="color: #FF9800;">users/</span>
│       │           └── ApiCommonsApplication.java
│       └── <span style="color: #2196F3;">Resources/</span>
│           ├── application.yml
│           └── logback-spring.xml
├── Dockerfile
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
</pre>

<h2>Estructura de Componentes</h2>

<p>Cada componente (acceso, calendar, manual, etc.) sigue la siguiente estructura:</p>

<pre>
<span style="color: #FF9800;">component/</span>
├── <span style="color: #9C27B0;">controller/</span>
│   └── ComponentController.java
├── <span style="color: #E91E63;">model/</span>
│   └── Component.java
├── <span style="color: #795548;">repository/</span>
│   └── ComponentRepository.java
└── <span style="color: #009688;">service/</span>
    └── ComponentService.java
</pre>

<h2>Descripción de la Estructura</h2>

<ul>
  <li><strong>deploy/</strong>: Contiene archivos relacionados con el despliegue de la aplicación.</li>
  <li><strong>src/main/java/</strong>: Contiene el código fuente principal de la aplicación.</li>
  <li><strong>components/</strong>: Agrupa los diferentes módulos de la aplicación.</li>
  <li><strong>Resources/</strong>: Contiene archivos de configuración.</li>
  <li><strong>Dockerfile</strong>: Define cómo se debe construir el contenedor Docker para la aplicación.</li>
  <li><strong>pom.xml</strong>: Archivo de configuración de Maven para gestionar dependencias y build.</li>
</ul>

<h2>Componentes</h2>

<p>Cada componente está organizado siguiendo el patrón MVC (Model-View-Controller) con capas adicionales:</p>

<ul>
  <li><strong>Controller</strong>: Maneja las solicitudes HTTP y define los endpoints de la API.</li>
  <li><strong>Model</strong>: Define las entidades de datos.</li>
  <li><strong>Repository</strong>: Proporciona métodos para acceder a la base de datos.</li>
  <li><strong>Service</strong>: Contiene la lógica de negocio.</li>
</ul>
