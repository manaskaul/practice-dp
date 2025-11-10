# practice-dp

A Java project for practicing dp algorithms and data structures. This repository contains implementations for dp algos organized for learning and experimentation.

## Project Structure

```
practice-dp/
├── topic0_01_knapsack/        # Pattern 1 : 0-1 Knapsack
├── .vscode/                   # VS Code tasks and launch configs
├── .gitignore
└── README.md
```

## How to Build & Run

### Prerequisites

- Java 11 or higher
- [VS Code](https://code.visualstudio.com/) (recommended for provided tasks/configs)

### Build

From the workspace root, run:

```sh
# Compile all Java files to 'out' directory
javac -d out topic0_01_knapsack/*.java
```

Or use the VS Code build task:

- Open the Command Palette (`Cmd+Shift+P`)
- Run `Tasks: Run Build Task` and select `javac-build`

### Run

Use the provided launch configurations in VS Code:

- **Knapsack**: Runs knapsack dp demos

Or run manually:

```sh
java -cp out topic1_01_knapsack.Knapsack
```

## Example Usage

- See `topic1_01_knapsack.Knapsack.java` for dp knapsack.

## Contributing

Feel free to fork and extend with new algorithms, tests, or optimizations!

## License

This project is for educational purposes.

---

**Note:** Directory/package names may differ from file paths. Adjust build/run commands if