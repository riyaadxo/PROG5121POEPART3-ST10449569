PROG5121 POE Part 3 - MessageManager

## Project Overview

This Java project implements a `MessageManager` class that manages chat messages for the QuickChat application. It supports:

- Reading messages from JSON files.
- Storing messages internally with validation.
- Searching, deleting, and reporting on messages.
- Saving messages back to JSON.
- Tracking message statuses like `sent`, `stored`, and `disregard`.

The project uses `json-simple` for JSON parsing and requires Java 8 or higher.

---

## Features

- **Read messages from JSON:** Safely loads message data, handling missing or malformed fields.
- **Add new messages:** Supports full and partial data with timestamp management.
- **Search messages:** By recipient or message ID with formatted outputs.
- **Delete messages:** By message hash.
- **Generate reports:** Summary of sent messages.
- **Count messages:** Based on status categories.
- **Save messages:** Write all stored messages back to JSON files.

---

## Prerequisites

- Java Development Kit (JDK) 8 or above.
- NetBeans IDE 8+ (or any Java IDE).
- `json-simple` library for JSON handling.

---

## Usage Instructions

1. Clone or download this repository.
2. Open the project in your IDE (NetBeans recommended).
3. Ensure `json-simple` is added to your project libraries.
4. Update the file path to your JSON message file in `readStoredMessagesFromJSON`.
5. Use the provided methods to manage your messages.
6. Call `saveToJSON` to export messages back to a file.

---

## Notes

- This project assumes timestamps are formatted as `yyyy-MM-dd HH:mm:ss`.
- The `status` field accepts only `sent`, `stored`, or `disregard`.
- The `populateMessage` method prevents adding null or invalid status messages.
- The JSON handling uses simple parsing and writing — consider upgrading to Gson or Jackson for more complex needs.

---

## References

- The Independent Institute of Education (The IIE). 2025. *PROG5121: Programming 5121 - Part 3: Message Management*. Course Material. The IIE.
- Oracle. 2024. *Java Platform, Standard Edition 8 API Specification*. [online] Available at: https://docs.oracle.com/javase/8/docs/api/ [Accessed 16 June 2025].
- Stearyl, D. (Year). *JSON-simple User Guide*. [online] Available at: https://github.com/fangyidong/json-simple [Accessed 16 June 2025].
