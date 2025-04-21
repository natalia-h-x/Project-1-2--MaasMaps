# Project 1-2: MaasMaps

MaasMaps is a Java-based application developed within the framework of Maastricht University's carriculum, to simulate and evaluate public transport accessibility in Maastricht using bus network data. The application processes GTFS datasets, builds a route network graph, and computes the fastest routes between postal codes using shortest path algorithms like Dijkstra and A*. It features a custom map UI, bus stop visualizations, and an accessibility metric per region.

---

## Key Features

- Map-based interface with postal code input
- Bus routing using real GTFS data
- Dijkstra and A* algorithm implementations
- Visual display of shortest paths with optional transfers
- Extensive testing with JUnit and benchmarking against Google Maps

---

## How to Run

### Requirements

- Java 17 or later
- JavaFX SDK 20.0.1
- SQLite (bundled)
- JSON Simple library
- GTFS data zip (available via release)

### Instructions

1. **Clone the repository**

   ```bash
   git clone https://github.com/natalia-h-x/Project-1-2---MaasMaps.git
   cd Project-1-2---MaasMaps
   Run the application

2. **Set up JavaFX path**

If using VS Code, modify the launch.json file:

json
"vmArgs": "--module-path \"C:/Program Files/Java/javafx-sdk-21.0.1/lib\" --add-modules javafx.controls,javafx.fxml"


3. **Run the application**

Open and run Main.java in your preferred Java IDE 

4. **GTFS Note**

Make sure to extract the GTFS files into:

resources/gtfs/


#### Contributors

- Sheena Gallagher

- Alexandra Plishkin

- Sian Lodde

- Natalia Hadjisoteriou

- Arda Ayyildizbayraktar

- Meriç Uruş

- Kimon Navridis


