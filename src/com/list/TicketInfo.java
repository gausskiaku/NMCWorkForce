package com.list;

import org.ksoap2.serialization.SoapObject;

import android.text.format.Time;

public class TicketInfo {

	

	String Trouble_Ticket_ID;
    String Submitter;
    String Dispatch_To;
    String Last_Modified_By;
    int Status;
    String Category;
    String Summary;
    String Dispatch_Group;
    String Handover_Mode;
    String Priority;
    String Process_Type;
    String Description;
    String Root_Cause;
    String Resolution_Method;
    String Handover_To;
    Time Start_Time;
    String Region;

    
    
    
   // public TicketInfo(SoapObject soapObject) {
//		new Deserialization().
//	}

	public String getTrouble_Ticket_ID() {
        return Trouble_Ticket_ID;
    }

    public void setTrouble_Ticket_ID(String Trouble_Ticket_ID) {
        this.Trouble_Ticket_ID = Trouble_Ticket_ID;
    }

    public String getSubmitter() {
        return Submitter;
    }

    public void setSubmitter(String Submitter) {
        this.Submitter = Submitter;
    }

    public String getDispatch_To() {
        return Dispatch_To;
    }

    public void setDispatch_To(String Dispatch_To) {
        this.Dispatch_To = Dispatch_To;
    }

    public String getLast_Modified_By() {
        return Last_Modified_By;
    }

    public void setLast_Modified_By(String Last_Modified_By) {
        this.Last_Modified_By = Last_Modified_By;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String Summary) {
        this.Summary = Summary;
    }

    public String getDispatch_Group() {
        return Dispatch_Group;
    }

    public void setDispatch_Group(String Dispatch_Group) {
        this.Dispatch_Group = Dispatch_Group;
    }

    public String getHandover_Mode() {
        return Handover_Mode;
    }

    public void setHandover_Mode(String Handover_Mode) {
        this.Handover_Mode = Handover_Mode;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String Priority) {
        this.Priority = Priority;
    }

    public String getProcess_Type() {
        return Process_Type;
    }

    public void setProcess_Type(String Process_Type) {
        this.Process_Type = Process_Type;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getRoot_Cause() {
        return Root_Cause;
    }

    public void setRoot_Cause(String Root_Cause) {
        this.Root_Cause = Root_Cause;
    }

    public String getResolution_Method() {
        return Resolution_Method;
    }

    public void setResolution_Method(String Resolution_Method) {
        this.Resolution_Method = Resolution_Method;
    }

    public String getHandover_To() {
        return Handover_To;
    }

    public void setHandover_To(String Handover_To) {
        this.Handover_To = Handover_To;
    }

    public Time getStart_Time() {
        return Start_Time;
    }

    public void setStart_Time(Time Start_Time) {
        this.Start_Time = Start_Time;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String Region) {
        this.Region = Region;
    }
    
	
	
}
