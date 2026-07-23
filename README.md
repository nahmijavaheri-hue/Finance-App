# Simple Finance

### A personal finance & expense tracker, written from scratch in Java

**Simple Finance** is a Java desktop application for tracking income and expenses. Transactions live inside an account, are tagged with a category (food, rent, shopping, paycheque, transfers, etc.), and can be filtered, sorted, and summarized — with everything backed by JSON persistence and a Swing GUI that graphs income vs. expenses in real time.

I built this to get hands-on with object-oriented design, file persistence, and building a real desktop UI in Java — starting as a console app and growing into a full graphical interface.

---

## Features

- **Transaction tracking** — add income, expenses, and refunds with an amount, date/time, and category
- **Categorized spending** — nine built-in categories (paycheque, other income, transfers in/out, food, rent, shopping, other expenses), each tied to a transaction type
- **Filtering & sorting** — filter by category or type, sort by date (newest/oldest) or amount (largest/smallest)
- **Monthly summaries** — average income and average expenses for a given month/year
- **Live balance tracking** — running account balance updates as transactions are added or removed
- **JSON persistence** — save and reload the entire account state to/from disk
- **Event logging** — every mutation (transaction added/removed/cleared) is recorded via a singleton `EventLog`, printed out on close
- **Swing GUI** — scrollable transaction list, add/remove controls, a live income-vs-expenses bar chart, and save/load buttons

## Using the app

- The center panel shows a scrolling list of every transaction currently recorded in the account.
- **Add Transaction** (right sidebar) opens a form to record a new transaction — amount, category, date.
- Select a transaction in the list and click **Remove Selected** to delete it.
- The right-hand panel renders a live **Income vs Expenses** bar chart that updates as transactions change.
- **Save Data** / **Load Data** (bottom navigation) persist the account to `data/account.json` and reload it.

## Architecture

```
src/main/
├── model/
│   ├── Account.java              — owns the transaction list, balance, filtering/sorting
│   ├── Transaction.java          — a single income/expense/refund entry
│   ├── TransactionType.java      — INCOME / EXPENSE / REFUND
│   ├── TransactionCategory.java  — spending categories, each mapped to a type
│   ├── SortOrder.java            — NEWEST / OLDEST / LARGEST / SMALLEST
│   ├── Event.java                — a single logged event (timestamp + description)
│   └── EventLog.java             — singleton log of everything that's happened to an account
├── persistence/
│   ├── Writable.java             — interface for JSON-serializable model objects
│   ├── JsonWriter.java           — writes an Account out to disk as JSON
│   └── JsonReader.java           — reads an Account back in from a JSON file
└── ui/
    ├── Main.java                 — entry point
    ├── SimpleFinanceApp.java     — console-based interface
    └── SimpleFinanceGUI.java     — Swing GUI: transaction list, add/remove, live bar chart, save/load
```

Each model class enforces its own invariants (e.g. `Account` won't add a duplicate transaction, `Transaction` amounts must be positive), and `Account`/`Transaction` implement `Writable` so persistence stays decoupled from the model logic.

## Design notes

`Account` currently calls directly into `EventLog` whenever a transaction changes. Given more time, I'd decouple that: have `Account` just emit change notifications and let a separate listener translate those into log entries, so the model doesn't need to know how logging works — better for testability and separation of concerns.

## Building & running

Requires a JDK (17+) and the JARs in `lib/` on the classpath (`org.json` for JSON persistence, JUnit for tests).

```bash
javac -cp "lib/*" -d bin $(find src/main -name "*.java")
java -cp "bin:lib/*" ui.Main
```

On Windows, replace the `:` in the classpath with `;`.

## Tests

Unit tests cover the model (`Account`, `Transaction`) and persistence layer (`JsonReader`, `JsonWriter`) under `src/test`, run via the bundled JUnit console launcher in `lib/`.
