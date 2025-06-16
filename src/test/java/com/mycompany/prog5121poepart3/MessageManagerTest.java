package com.mycompany.prog5121poepart3;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class MessageManagerTest {
    private MessageManager manager;
    private File tempFile;

    @Before
    public void setUp() throws IOException {
        manager = new MessageManager();
        
       
        manager.populateMessage("Did you get the cake?", "sent", "+27834557896", "01:1:DIDCAKE", "0838880001", LocalDateTime.now());
        manager.populateMessage("Where are you? You are late! I have asked you to be on time.", "stored", "+27838884567", "02:2:WHERETIME", "0838880002", LocalDateTime.now());
        manager.populateMessage("Yohoooo, I am at your gate.", "disregard", "+27834484567", "03:3:YOHOOGATE", "0838880003", LocalDateTime.now());
        manager.populateMessage("It is dinner time!", "sent", "+27838884567", "04:4:ITTIME", "0838884567", LocalDateTime.now());
        manager.populateMessage("Ok, I am leaving without you.", "stored", "+27838884567", "05:5:OKYOU", "0838880005", LocalDateTime.now());

        
        tempFile = File.createTempFile("messages", ".json");
        JSONArray messages = new JSONArray();
        
        JSONObject msg1 = new JSONObject();
        msg1.put("message", "Test message from JSON");
        msg1.put("messageHash", "06:6:TESTJSON");
        msg1.put("messageID", "0838889999");
        msg1.put("recipient", "+27839999999");
        messages.add(msg1);
        
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(messages.toJSONString());
        }
    }

    @After
    public void tearDown() {
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete();
        }
        manager = null;
    }

    @Test
    public void testSentMessagesArrayPopulated() {
        assertEquals(2, manager.getSentMessagesCount());
        assertTrue(manager.searchByMessageID("0838880001").contains("Did you get the cake?"));
        assertTrue(manager.searchByMessageID("0838884567").contains("It is dinner time!"));
    }

    @Test
    public void testStoredMessagesArrayPopulated() {
        assertEquals(2, manager.getStoredMessagesCount());
        assertTrue(manager.searchByMessageID("0838880002").contains("Where are you?"));
        assertTrue(manager.searchByMessageID("0838880005").contains("Ok, I am leaving"));
    }

    @Test
    public void testDisregardedMessagesArrayPopulated() {
        assertEquals(1, manager.getDisregardedMessagesCount());
        assertTrue(manager.searchByMessageID("0838880003").contains("Yohoooo"));
    }

    @Test
    public void testLongestMessage() {
        String longMessage = "Where are you? You are late! I have asked you to be on time.";
        manager.populateMessage(longMessage, "sent", "+27838884567", "06:6:LONGEST", "0838880006", LocalDateTime.now());
        String longest = manager.displayLongestMessage();
        assertEquals(longMessage, longest);
    }

    @Test
    public void testSearchByMessageID() {
        assertEquals("Message: It is dinner time!", manager.searchByMessageID("0838884567"));
        assertEquals("Message ID not found.", manager.searchByMessageID("9999999999"));
        assertEquals("Invalid message ID.", manager.searchByMessageID(null));
    }

    @Test
    public void testFindMessagesByRecipient() {
        List<String> results = manager.findMessagesByRecipient("+27838884567");
        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(m -> m.contains("Where are you?")));
        assertTrue(results.stream().anyMatch(m -> m.contains("Ok, I am leaving")));

        assertTrue(manager.findMessagesByRecipient("+99999999999").isEmpty());
        assertTrue(manager.findMessagesByRecipient(null).isEmpty());
    }

    @Test
    public void testDeleteByHash() {
        assertTrue(manager.deleteByHash("02:2:WHERETIME"));
        assertEquals("Message ID not found.", manager.searchByMessageID("0838880002"));

        assertFalse(manager.deleteByHash("NONEXISTENT"));
        assertFalse(manager.deleteByHash(null));
    }

    @Test
    public void testGenerateReport() {
        String report = manager.generateReport();
        assertTrue(report.contains("Message Hash: 01:1:DIDCAKE"));
        assertTrue(report.contains("Message ID: 0838880001"));
        assertTrue(report.contains("Recipient: +27834557896"));
        assertTrue(report.contains("Message: Did you get the cake?"));
        assertTrue(report.contains("Message Hash: 04:4:ITTIME"));
        assertTrue(report.contains("Message ID: 0838884567"));
        assertTrue(report.contains("Recipient: +27838884567"));
        assertTrue(report.contains("Message: It is dinner time!"));
    }

    @Test
    public void testReadStoredMessagesFromJSON() throws Exception {
        manager.readStoredMessagesFromJSON(tempFile.getAbsolutePath());
        assertEquals(3, manager.getStoredMessagesCount()); // original 2 + 1 from JSON
        assertTrue(manager.searchByMessageID("0838889999").contains("Test message from JSON"));
    }

    @Test(expected = IOException.class)
    public void testReadStoredMessagesFromJSONFileNotFound() throws Exception {
        manager.readStoredMessagesFromJSON("nonexistent_file.json");
    }

    private static class JSONArray {

        public JSONArray() {
        }

        private void add(JSONObject msg1) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private char[] toJSONString() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }

    private static class JSONObject {

        public JSONObject() {
        }

        private void put(String message, String test_message_from_JSON) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }
}
