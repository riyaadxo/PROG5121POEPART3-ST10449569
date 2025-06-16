package com.mycompany.prog5121poepart3;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MessageManager {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final List<Message> messages = new ArrayList<>();

    public void readStoredMessagesFromJSON(String filePath) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(filePath)) {
            JSONArray messageArray = (JSONArray) parser.parse(reader);

           for (var element : messageArray) {
    JSONObject message = (JSONObject) element;


                String content = messageJson.get("message") != null ? messageJson.get("message").toString() : "";
                String status = messageJson.get("status") != null ? messageJson.get("status").toString() : "";
                String recipient = messageJson.get("recipient") != null ? messageJson.get("recipient").toString() : "";
                String hash = messageJson.get("messageHash") != null ? messageJson.get("messageHash").toString() : "";
                String id = messageJson.get("messageID") != null ? messageJson.get("messageID").toString() : "";
                String sender = messageJson.get("sender") != null ? messageJson.get("sender").toString() : "Unknown";
                String tsStr = messageJson.get("timestamp") != null ? messageJson.get("timestamp").toString() : null;

                LocalDateTime ts;
                try {
                    ts = tsStr != null ? LocalDateTime.parse(tsStr, FORMATTER) : LocalDateTime.now();
                } catch (Exception e) {
                    ts = LocalDateTime.now();
                }

                messages.add(new Message(content, status, recipient, hash, id, sender, ts));
            }
        }
    }

    public boolean populateMessage(String content, String status, String recipient, String hash, String id, String sender, LocalDateTime timestamp) {
        if (Arrays.asList(content, status, recipient, hash, id, sender, timestamp).contains(null)) {
            return false;
        }

        if (!isValidStatus(status)) {
            return false;
        }

        return messages.add(new Message(content, status, recipient, hash, id, sender, timestamp));
    }

    public boolean populateMessage(String content, String status, String recipient, String hash, String id, LocalDateTime now) {
        return populateMessage(content, status, recipient, hash, id, "Unknown", LocalDateTime.now());
    }

    public List<String> findMessagesByRecipient(String recipient) {
        List<String> result = new ArrayList<>();
        for (Message m : messages) {
            if (recipient.equalsIgnoreCase(m.recipient)) {
                result.add(m.content);
            }
        }
        return result;
    }

    public String displayLongestMessage() {
        return messages.stream()
                .filter(m -> "sent".equalsIgnoreCase(m.status))
                .map(m -> m.content)
                .max(Comparator.comparingInt(String::length))
                .orElse("No sent messages found.");
    }

    public String searchByMessageID(String searchID) {
        return messages.stream()
                .filter(m -> searchID.equals(m.messageId))
                .findFirst()
                .map(m -> String.format(
                        "Message: %s%nTo: %s%nTime: %s",
                        m.content, m.recipient,
                        m.timestamp.format(FORMATTER)))
                .orElse("Message ID not found.");
    }

    public List<String> searchByRecipientDetails(String recipient) {
        List<String> results = new ArrayList<>();
        messages.stream()
                .filter(m -> recipient.equalsIgnoreCase(m.recipient))
                .forEach(m -> results.add(
                        String.format("Message: %s%nID: %s%nTime: %s",
                                m.content, m.messageId,
                                m.timestamp.format(FORMATTER))));
        return results;
    }

    public boolean deleteByHash(String hash) {
        return messages.removeIf(m -> hash.equals(m.messageHash));
    }

    public String generateReport() {
        StringBuilder sb = new StringBuilder();

        messages.stream()
                .filter(m -> "sent".equalsIgnoreCase(m.status))
                .forEach(m -> sb.append(String.format(
                        "Message Hash: %s%nMessage ID: %s%nRecipient: %s%nSender: %s%nTime: %s%nMessage: %s%n%n",
                        m.messageHash, m.messageId, m.recipient, m.sender,
                        m.timestamp.format(FORMATTER), m.content)));

        return sb.length() == 0
                ? "No sent messages available."
                : sb.toString();
    }

    public long getSentMessagesCount() {
        return messages.stream()
                .filter(m -> "sent".equalsIgnoreCase(m.status))
                .count();
    }

    public long getDisregardedMessagesCount() {
        return messages.stream()
                .filter(m -> "disregard".equalsIgnoreCase(m.status))
                .count();
    }

    public long getStoredMessagesCount() {
        return messages.stream()
                .filter(m -> "stored".equalsIgnoreCase(m.status))
                .count();
    }

    public void saveToJSON(String filePath) throws IOException {
        JSONArray arr = new JSONArray();

        for (Message m : messages) {
            JSONObject o = new JSONObject();
            o.put("message", m.content);
            o.put("status", m.status);
            o.put("recipient", m.recipient);
            o.put("messageHash", m.messageHash);
            o.put("messageID", m.messageId);
            o.put("sender", m.sender);
            o.put("timestamp", m.timestamp.format(FORMATTER));
            arr.add(o);
        }

        try (FileWriter fw = new FileWriter(filePath)) {
            fw.write(arr.toJSONString());
            fw.flush();
        }
    }

    private boolean isValidStatus(String status) {
        return status != null &&
                (status.equalsIgnoreCase("sent")
                        || status.equalsIgnoreCase("stored")
                        || status.equalsIgnoreCase("disregard"));
    }

    List<String> searchByRecipient(String recipient) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    private static final class Message {
        final String content;
        final String status;
        final String recipient;
        final String messageHash;
        final String messageId;
        final String sender;
        final LocalDateTime timestamp;

        Message(String content, String status, String recipient, String messageHash, String messageId, String sender, LocalDateTime timestamp) {
            this.content = content;
            this.status = status;
            this.recipient = recipient;
            this.messageHash = messageHash;
            this.messageId = messageId;
            this.sender = sender;
            this.timestamp = timestamp;
        }
    }

    private static class ParseException extends Exception {

        public ParseException() {
        }
    }

    private static class JSONArray {

        public JSONArray() {
        }

        private char[] toJSONString() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private void add(JSONObject o) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }

    private static class JSONObject {

        public JSONObject() {
        }

        private void put(String message, String content) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private Object get(String message) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }

    private static class messageJson {

        private static Object get(String message) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public messageJson() {
        }
    }
}
