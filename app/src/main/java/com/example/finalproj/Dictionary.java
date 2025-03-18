package com.example.finalproj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dictionary {

    private Map<String, String> words;
    private List<String> wordList;  // Maintains word order


    public Dictionary(int num){
        words = new HashMap<>();
        wordList = new ArrayList<>();
        if(num == 0){
            addWord("Vindue", "Window");
            addWord("Sort", "Black");
            addWord("Lærer", "Teacher");
            addWord("Ven", "Friend");
            addWord("Dør", "Door");
            addWord("Stjerne", "Star");
            addWord("Familie", "Family");
            addWord("Hus", "House");
            addWord("Regn", "Rain");
            addWord("Væg", "Wall");
        }
        else if(num == 1){
            addWord("Fugl", "Bird");
            addWord("Bror", "Brother");
            addWord("Dag", "Day");
            addWord("Måne", "Moon");
            addWord("Seng", "Bed");
            addWord("Papir", "Paper");
            addWord("Vinter", "Winter");
            addWord("Brød", "Bread");
            addWord("Sne", "Snow");
            addWord("Bil", "Car");
        }
        else if(num == 2){
            addWord("Skole", "School");
            addWord("Fisk", "Fish");
            addWord("Stol", "Chair");
            addWord("Hvid", "White");
            addWord("Nat", "Night");
            addWord("Barn", "Child");
            addWord("Mad", "Food");
            addWord("Far", "Father");
            addWord("Bord", "Table");
            addWord("Bog", "Book");
        }
        else if(num == 3){
            addWord("Træ", "Tree");
            addWord("Ost", "Cheese");
            addWord("Sol", "Sun");
            addWord("Sommer", "Summer");
            addWord("Hest", "Horse");
            addWord("Kat", "Cat");
            addWord("Søster", "Sister");
            addWord("Hund", "Dog");
            addWord("Vand", "Water");
            addWord("Mor", "Mother");
        }
    }

    private void addWord(String word, String meaning) {
        if (!words.containsKey(word)) {  // Avoid duplicates
            wordList.add(word);
            words.put(word, meaning);
        }
    }

    private String getMeaning(String word) {
        return words.getOrDefault(word, "Word not found");
    }

    // Get word by index
    private String getWordByIndex(int index) {
        if (index >= 0 && index < wordList.size()) {
            return wordList.get(index);
        }
        return "Index out of range";
    }

    // Get meaning by index
    private String getMeaningByIndex(int index) {
        if (index >= 0 && index < wordList.size()) {
            return words.get(wordList.get(index));
        }
        return "Index out of range";
    }

    // Get dictionary size
    private int size() {
        return wordList.size();
    }

    public String[] getAllWords() {
        return wordList.toArray(new String[0]);
    }

    // Get all meanings (values) as an array
    public String[] getAllMeanings() {
        List<String> meanings = new ArrayList<>();
        for (String word : wordList) {
            meanings.add(words.get(word));
        }
        return meanings.toArray(new String[0]);
    }
}
