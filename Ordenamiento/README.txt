

Estructura del proyecto
src/
├── main/
│   ├── java/
│   │   ├── controllers/          # Controladores para la lógica de la interfaz
│   │   ├── models/              # Modelos de datos y lógica de negocio
│   │   │   ├── sorting/         # Todos los algoritmos de ordenamiento
│   │   │   │   ├── BucketSort
│   │   │   │   ├── QuickSort
│   │   │   │   ├── MergeSort
│   │   │   │   ├── CountingSort
│   │   │   │   ├── HeapSort
│   │   │   │   ├── InsertionSort
│   │   │   │   ├── RadixSort
│   │   │   │   └── SelectionSort
│   │   ├── views/               # Interfaces de usuario
│   │   │   ├── components/      # Componentes reutilizables
│   │   │   └── MainFrame  # Ventana principal
│   │   ├── utils/               # Utilidades comunes
│   │   └── Main                 # Clase principal
│   ├── resources/               # Recursos (imágenes, archivos de configuración)
│   │   ├── images/
│   │   └── strings.properties   # Internacionalización
└── test/
    └── java/                    # Pruebas unitarias
        └── sorting/             # Pruebas para cada algoritmo


Guía de Colaboración
Para mantener el proyecto organizado y asegurar la calidad del código, seguimos un flujo de trabajo basado en ramas de funcionalidad (feature branches).

Flujo de Trabajo General
Nunca se suben cambios directamente a la rama main. Esta rama siempre debe contener una versión estable y funcional del proyecto.

Para cada nueva tarea (en este caso, implementar un algoritmo), se crea una nueva rama a partir de main.

Todo el trabajo se realiza en esa rama aislada.

Cuando la tarea está completa, se abre un Pull Request (PR) para proponer la integración de los cambios a main.

El código es revisado por otro miembro del equipo.

Una vez aprobado, el Pull Request se fusiona con main.

Nomenclatura de Ramas
Cada algoritmo de ordenamiento se desarrollará en su propia rama. Por favor, usa los siguientes nombres para mantener la consistencia:

feature/bucket-sort

feature/quick-sort

feature/merge-sort

feature/counting-sort

feature/heap-sort

feature/insertion-sort

feature/radix-sort

feature/selection-sort

Pasos para Añadir un Algoritmo
Sigue estos pasos desde tu terminal para contribuir. (Ejemplo usando quick-sort).

1. Clona el repositorio (si es la primera vez):

git clone https://github.com/tu-usuario/tu-repositorio.git
cd tu-repositorio

2. Asegúrate de estar en la rama main y tener la última versión:

git checkout main
git pull origin main

3. Crea tu rama de trabajo y muévete a ella:

# Reemplaza 'feature/quick-sort' con el nombre de la rama que te asignaron
git checkout -b feature/quick-sort

4. ¡A programar!

Abre el proyecto en tu editor de código.

Implementa tu algoritmo en el archivo correspondiente (ej. src/main/java/models/sorting/QuickSort.java).

5. Guarda tus cambios (Commit):

Añade los archivos que modificaste.

git add .

Crea un "commit" con un mensaje descriptivo.

git commit -m "Implementación del algoritmo QuickSort"

6. Sube tu rama a GitHub:

# La primera vez que subes la rama, usa este comando:
git push -u origin feature/quick-sort
