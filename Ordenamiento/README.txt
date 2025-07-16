

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