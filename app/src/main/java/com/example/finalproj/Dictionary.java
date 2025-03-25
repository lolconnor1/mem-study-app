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
            addWord("Ske", "Spoon");
            addWord("Gaffel", "Fork");
            addWord("Tallerken", "Plate");
            addWord("Vindue", "Window");
            addWord("Tag", "Roof");
            addWord("Gulv", "Floor");
            addWord("Skov", "Forest");
            addWord("Flod", "River");
            addWord("Hav", "Sea");
            addWord("Græs", "Grass");
            addWord("Bakke", "Hill");
            addWord("Måne", "Moon");
            addWord("Skygge", "Shadow");
            addWord("Lyn", "Lightning");
            addWord("Torden", "Thunder");
        }
        else if(num == 1){
            addWord("Nabo", "Neighbor");
            addWord("Ven", "Friend");
            addWord("Fjende", "Enemy");
            addWord("Skulder", "Shoulder");
            addWord("Knæ", "Knee");
            addWord("Albue", "Elbow");
            addWord("Håndled", "Wrist");
            addWord("Ankel", "Ankle");
            addWord("Ryg", "Back");
            addWord("Bryst", "Chest");
            addWord("Tunge", "Tongue");
            addWord("Kind", "Cheek");
            addWord("Øjenbryn", "Eyebrow");
            addWord("Negl", "Nail");
            addWord("Pande", "Forehead");
        }
        else if(num == 2){
            addWord("Skubbe", "Push");
            addWord("Trække", "Pull");
            addWord("Springe", "Jump");
            addWord("Falde", "Fall");
            addWord("Vride", "Twist");
            addWord("Snurre", "Spin");
            addWord("Skælve", "Tremble");
            addWord("Hoste", "Cough");
            addWord("Gabe", "Yawn");
            addWord("Grine", "Laugh");
            addWord("Hviske", "Whisper");
            addWord("Fnyse", "Snort");
            addWord("Sukke", "Sigh");
            addWord("Ryste", "Shake");
            addWord("Bøje", "Bend");
        }
        else if(num == 3){
            addWord("Drøm", "Dream");
            addWord("Løgn", "Lie");
            addWord("Sandhed", "Truth");
            addWord("Skyld", "Guilt");
            addWord("Håb", "Hope");
            addWord("Skæbne", "Fate");
            addWord("Tvivl", "Doubt");
            addWord("Lykke", "Happiness");
            addWord("Sorg", "Sorrow");
            addWord("Vrede", "Anger");
            addWord("Tålmodighed", "Patience");
            addWord("Begær", "Desire");
            addWord("Venskab", "Friendship");
            addWord("Enshed", "Loneliness");
            addWord("Mørke", "Darkness");
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
