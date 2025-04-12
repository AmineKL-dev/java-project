ventes-analyse/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── ventes/
│   │   │           ├── App.java                  # Point d'entrée principal
│   │   │           ├── config/                   # Configuration
│   │   │           │   ├── DatabaseConfig.java   # Paramètres de connexion DB
│   │   │           │   └── AppConfig.java        # Configuration générale
│   │   │           ├── controllers/
│   │   │           │   ├── MainController.java   # Contrôleur principal
│   │   │           │   ├── AuthController.java   # Gestion authentification
│   │   │           │   ├── SaleController.java   # Gestion des ventes
│   │   │           │   ├── ImportController.java # Import CSV/DB
│   │   │           │   └── ChartController.java  # Gestion des graphiques
│   │   │           ├── models/
│   │   │           │   ├── Sale.java             # Modèle de données Vente
│   │   │           │   ├── User.java             # Modèle Utilisateur
│   │   │           │   ├── services/
│   │   │           │   │   ├── AuthService.java # Service d'authentification
│   │   │           │   │   ├── SaleService.java # Gestion des ventes
│   │   │           │   │   ├── ImportService.java # Importation données
│   │   │           │   │   └── ChartService.java # Génération graphiques
│   │   │           │   ├── repositories/
│   │   │           │   │   ├── SaleRepository.java # Accès DB pour les ventes
│   │   │           │   │   └── UserRepository.java # Accès DB pour les users
│   │   │           │   └── utils/
│   │   │           │       ├── CSVReader.java    # Lecture fichiers CSV
│   │   │           │       ├── PDFExporter.java  # Export PDF
│   │   │           │       └── ChartGenerator.java # Génération graphiques
│   │   │           └── views/
│   │   │               ├── components/           # Composants réutilisables
│   │   │               │   ├── ChartPane.java    # Panel personnalisé graphique
│   │   │               │   └── DataTable.java    # Tableau personnalisé
│   │   ├── resources/
│   │   │   ├── com/ventes/views/
│   │   │   │   ├── main.fxml                    # Interface principale
│   │   │   │   ├── login.fxml                  # Ecran de connexion
│   │   │   │   ├── sale_form.fxml              # Formulaire vente
│   │   │   │   ├── import_csv.fxml             # Importation CSV
│   │   │   │   └── charts/                     # Vues des graphiques
│   │   │   │       ├── sales_chart.fxml        # Graphique ventes
│   │   │   │       └── products_chart.fxml     # Graphique produits
│   │   │   ├── styles/
│   │   │   │   ├── main.css                   # Styles principaux
│   │   │   │   ├── charts.css                 # Styles graphiques
│   │   │   │   └── forms.css                 # Styles formulaires
│   │   │   ├── data/                          # Exemples de fichiers CSV
│   │   │   │   └── sample_sales.csv          
│   │   │   └── config.properties              # Fichier de configuration
│   │   └── database/
│   │       └── scripts/                       # Scripts SQL
│   │           ├── create_tables.sql          # Création structure DB
│   │           └── sample_data.sql           # Données d'exemple
│   └── test/
│       └── java/com/ventes/
│           ├── services/                      # Tests des services
│           └── repositories/                  # Tests des repositories
├── target/                                   # Généré par Maven
├── pom.xml                                   # Configuration Maven
└── README.md                                 # Documentation projet
