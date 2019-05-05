package com.example.final_mobile_device_programming;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaledrone.lib.Listener;
import com.scaledrone.lib.Member;
import com.scaledrone.lib.Room;
import com.scaledrone.lib.RoomListener;
import com.scaledrone.lib.Scaledrone;


public class message extends AppCompatActivity implements RoomListener {
    private String channelID = "O86HApJ3xsOaOg7f";
    private String roomName = "final";
    private EditText editText;
    private Scaledrone scaledrone;
    private MessageAdapter MessageAdapter;
    private ListView messagesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    editText = (EditText) findViewById(R.id.editText);

    MessageAdapter = new MessageAdapter(this);
    messagesView = (ListView) findViewById(R.id.messages_view);
        messagesView.setAdapter(MessageAdapter);

    MemberData data = new MemberData(getRandomName();)

    scaledrone = new Scaledrone(channelID, data);
        scaledrone.connect(new Listener() {
        @Override
        public void onOpen() {
            System.out.println("Scaledrone connection open");
            scaledrone.subscribe(roomName, MainActivity.this);
        }

        @Override
        public void onOpenFailure(Exception ex) {
            System.err.println(ex);
        }

        @Override
        public void onFailure(Exception ex) {
            System.err.println(ex);
        }

        @Override
        public void onClosed(String reason) {
            System.err.println(reason);
        }
    });
}

    public void sendMessage(View view) {
        String message = editText.getText().toString();
        if (message.length() > 0) {
            scaledrone.publish(roomName, message);
            editText.getText().clear();
        }
    }

    @Override
    public void onOpen(Room room) {
        System.out.println("Conneted to room");
    }

    @Override
    public void onOpenFailure(Room room, Exception ex) {
        System.err.println(ex);
    }

    @Override
    public void onMessage(Room room, com.scaledrone.lib.Message receivedMessage) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            final MemberData data = mapper.treeToValue(receivedMessage.getMember().getClientData(), MemberData.class);
            boolean belongsToCurrentUser = receivedMessage.getClientID().equals(scaledrone.getClientID());
            final user_message message = new user_message(receivedMessage.getData().asText(), data, belongsToCurrentUser);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MessageAdapter.add(message);
                    messagesView.setSelection(messagesView.getCount() - 1);
                }
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private String getRandomName() {
        String[] first = {"Abigail", "Anna", "Sage", "Korbyn", "Holly", "Diane", "Gina", "Mark", "Kristopher", "Zachary", "Dale", "Nathan", "Jerome", "Bonnie", "Andrew", "Harper", "Mary", "Scott", "Jennifer", "Ryan", "Sandy", "James", "Robert", "Matthew", "Angelia", "Kaitlin", "Dominic"};
        String[] last = {"Sova", "Trenda", "Stangel", "Perpich", "Larson", "Boike", "McKenna", "Ferrari", "Halbersadht", "Patterson", "Broden", "Hofstedt"};

        return (
                first[(int) Math.floor(Math.random() * first.length)] +
                        "_" +
                        last[(int) Math.floor(Math.random() * first.length)]
        );
    }

}

class MemberData {
    private String name;

    public MemberData(String name, String color) {
        this.name = name;

    }

    public MemberData() {
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "MemberData{" +
                "name='" + name + '\'' +
                '}';
    }
}


