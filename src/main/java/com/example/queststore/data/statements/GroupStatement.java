package com.example.queststore.data.statements;


import com.example.queststore.data.contracts.GroupEntry;
import com.example.queststore.data.contracts.MentorGroupEntry;

public class GroupStatement {

    public String selectAllGroups() {
        return "SELECT * FROM " + GroupEntry.TABLE_NAME + " WHERE group_name != 'No group';" ;
    }

    public String selectGroupByName() {
        return "SELECT * FROM " + GroupEntry.TABLE_NAME +
                " WHERE " + GroupEntry.GROUP_NAME + " = ?;" ;
    }

    public String selectGroupById() {
        return "SELECT * FROM " + GroupEntry.TABLE_NAME +
                " WHERE " + GroupEntry.ID + " = ?;" ;
    }

    public String insertGroupStatement() {

        return "INSERT INTO " + GroupEntry.TABLE_NAME + " (" +
                GroupEntry.GROUP_NAME + ") VALUES (?);" ;
    }

    public String deleteGroupStatement() {
        return "DELETE FROM " + GroupEntry.TABLE_NAME +
                " WHERE " + GroupEntry.ID + " = ?;" ;
    }

    public String selectGroupsNamesByMentorId() {
        return "SELECT * FROM " + GroupEntry.TABLE_NAME +
                " JOIN " + MentorGroupEntry.TABLE_NAME + " ON " +
                GroupEntry.TABLE_NAME + "." + GroupEntry.ID + " = " +
                MentorGroupEntry.TABLE_NAME + "." + MentorGroupEntry.ID_GROUP +
                " WHERE " + MentorGroupEntry.TABLE_NAME + "." + MentorGroupEntry.ID_MENTOR + " = ?;" ;
    }

    public String createTable() {
        return "CREATE TABLE " + GroupEntry.TABLE_NAME + " (" +
                GroupEntry.ID + " INTEGER PRIMARY KEY," +
                GroupEntry.GROUP_NAME + " TEXT NOT NULL UNIQUE);" ;
    }

    public String selectMentorAssignedToGroups() {
        return "SELECT * FROM " + MentorGroupEntry.TABLE_NAME + " ;";
    }

}
