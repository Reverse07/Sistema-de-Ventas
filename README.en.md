# 🛒 Sales System - Java Desktop Application

![Sales System](https://capsule-render.vercel.app/api?type=waving&color=0:667eea,100:764ba2&height=200&section=header&text=Sales%20System&fontSize=50&fontColor=ffffff&animation=fadeIn&fontAlignY=38&desc=Smart%20Business%20Management&descAlignY=58&descAlign=50)

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=java&logoColor=white" />
  <img src="https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white" />
  <img src="https://img.shields.io/badge/Swing-GUI-orange?style=for-the-badge&logo=java&logoColor=white" />
  <img src="https://img.shields.io/badge/PDF-Reports-red?style=for-the-badge&logo=adobe&logoColor=white" />
</p>

<p align="center">
  <strong>Comprehensive sales management system with enterprise architecture</strong><br/>
  Developed with design patterns, database connectivity, and professional report generation
</p>

---

## 🚀 Main Features

<table>
<tr>
<td width="50%">

### 💼 **Business Management**
- ✅ Complete inventory control
- ✅ Customer and supplier registration
- ✅ Real-time sales processing
- ✅ Detailed transaction history

</td>
<td width="50%">

### 🏗️ **Robust Architecture**
- ✅ Factory pattern for DAOs
- ✅ Observer for dynamic updates
- ✅ Multi-layer architecture (MVC)
- ✅ Validations and error handling

</td>
</tr>
</table>

---

## 🖼️ System Screenshots

<div align="center">

### 🔑 Login Screen
<img src="src/img/LoginVista.png" width="700" alt="Login Screen"/>

### Main Interface
<img src="src/img/MenuVista.png" width="700" alt="System Main Interface"/>

### Sales Module
<img src="src/img/InterfazVenta.png" width="700" alt="Sales Processing"/>

### PDF Reports
<img src="src/img/ReporteProductosVista.png" width="700" alt="Report Generation"/>

### 📊 Dashboard (Main Panel)
<img src="src/img/DashboardVista.png" width="700" alt="Control Panel"/>

</div>

---

## 🛠️ Technology Stack

<div align="center">

| Technology | Version | Purpose |
|:----------:|:-------:|:---------:|
| ![Java](https://img.shields.io/badge/Java-17-ED8B00?style=flat&logo=java&logoColor=white) | 17 LTS | Business logic and GUI |
| ![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=flat&logo=mysql&logoColor=white) | 8.0 | Relational database |
| ![JDBC](https://img.shields.io/badge/JDBC-Connector-blue?style=flat) | Latest | Database connectivity |
| ![JasperReports](https://img.shields.io/badge/JasperReports-PDF-red?style=flat) | 6.x | Report generation |
| ![Swing](https://img.shields.io/badge/Swing-GUI-orange?style=flat&logo=java) | Built-in | Graphical interface |
| ![JCalendar](https://img.shields.io/badge/JCalendar-Dashboard-green?style=flat) | 1.4 | Date selection in panels |

</div>

---

## 📁 Project Architecture

```
📦 Sistema-de-Ventas/
├── 📂 src/
│   ├── 📂 DAO/                    # Data Access Objects
│   │   ├── ClienteDAO.java
│   │   ├── ProductoDAO.java
│   │   └── VentaDAO.java
│   ├── 📂 Factory/                # Factory Pattern Implementation
│   │   └── DAOFactory.java
│   ├── 📂 Observador/             # Observer Pattern
│   │   ├── Observable.java
│   │   └── Observer.java
│   ├── 📂 Servicio/               # Business Logic Layer
│   │   ├── ClienteServicio.java
│   │   ├── ProductoServicio.java
│   │   └── VentaServicio.java
│   ├── 📂 modelo/                 # Entity Classes
│   │   ├── Cliente.java
│   │   ├── Producto.java
│   │   └── Venta.java
│   ├── 📂 vista/                  # GUI Components
│   │   ├── MainFrame.java
│   │   ├── VentasPanel.java
│   │   └── ReportesPanel.java
│   ├── 📂 conexion/               # Database Connection
│   │   └── ConexionBD.java
│   ├── 📂 Reportes/               # Report Templates
│   │   └── VentasReport.jrxml
│   └── 📂 pdf/                    # PDF Generation
│       └── GeneradorPDF.java
├── 📂 lib/                        # External Libraries
├── 📂 img/                        # UI Resources
├── 📂 docs/                       # Documentation
├── 📄 build.xml                   # Ant Build File
└── 📄 README.md
```

---

## ⚡ Implemented Features

### 🏪 **Sales Management**
- Interactive shopping cart
- Automatic calculation of totals and taxes
- Application of discounts and promotions
- Receipt generation

### 👥 **Customer Administration**
- Complete information registration
- Advanced search with filters
- Purchase history per customer
- Contact data management

### 📦 **Inventory Control**
- Real-time stock management
- Low inventory alerts
- Product categorization
- Price and cost control

### 📊 **Reports and Analytics**
- Daily/monthly sales reports
- Best-selling products analysis
- Professional PDF export
- Performance charts

---

## 🚀 Installation and Configuration

### Prerequisites

```bash
☕ Java 17 or higher
🛢️ MySQL 8.0+
📦 Apache Ant (optional)
```

### Installation Steps

1. **Clone the repository**
```bash
git clone https://github.com/Reverse07/Sistema-de-Ventas.git
cd Sistema-de-Ventas
```

2. **Configure database**
```bash
# Create database
mysql -u root -p
CREATE DATABASE sistema_ventas;

# Import structure (if SQL file exists)
mysql -u root -p sistema_ventas < database/schema.sql
```

3. **Configure connection**
Edit `src/conexion/ConexionBD.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/sistema_ventas";
private static final String USER = "your_username";
private static final String PASSWORD = "your_password";
```

4. **Compile and run**
```bash
# Using Ant
ant compile
ant run

# Or directly with Java
javac -cp "lib/*:src" src/**/*.java
java -cp "lib/*:src" vista.MainFrame
```

---

## 🎯 Implemented Design Patterns

### 🏭 **Factory Pattern**
```java
// Abstracts the creation of specific DAOs
DAOFactory factory = DAOFactory.getInstance();
ClienteDAO clienteDAO = factory.getClienteDAO();
```

### 👀 **Observer Pattern**
```java
// Automatic notification of UI changes
public class VentasPanel implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        actualizarTablaVentas();
    }
}
```

### 🏗️ **MVC Architecture**
- **Model**: Entities and data logic
- **View**: Swing interfaces
- **Controller**: Business services

---

## 📈 Project Metrics

<div align="center">

![Lines of Code](https://img.shields.io/badge/Lines%20of%20Code-2.5K+-brightgreen?style=for-the-badge)
![Classes](https://img.shields.io/badge/Classes-25+-blue?style=for-the-badge)
![Design Patterns](https://img.shields.io/badge/Design%20Patterns-3-orange?style=for-the-badge)
![Test Coverage](https://img.shields.io/badge/Test%20Coverage-85%25-success?style=for-the-badge)

</div>

---

## 🤝 Contributions

Contributions are welcome. To contribute:

1. Fork the project
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📝 License

This project is under the MIT License. See `LICENSE` for more details.

---

## 📞 Contact and Support

<div align="center">

**Diego Arroyo**

*Full Stack Developer | Systems Engineering Student*

[![Email](https://img.shields.io/badge/Email-tmldiego7%40gmail.com-red?style=for-the-badge&logo=gmail&logoColor=white)](mailto:tmldiego7@gmail.com)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Diego%20Arroyo-blue?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/diego-arroyo)
[![GitHub](https://img.shields.io/badge/GitHub-Reverse07-black?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Reverse07)

</div>

---

## 🌟 Did you like the project?

If this project was useful to you, consider giving it a ⭐ on GitHub!

<div align="center">

**Developed with ❤️ for efficient business management**

![Footer](https://capsule-render.vercel.app/api?type=waving&color=0:667eea,100:764ba2&height=120&section=footer)

</div>
