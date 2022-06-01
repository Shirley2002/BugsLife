package issue_tracker;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        int projectID;

        String title;
        String status;
        String description;
        String createdBy;
        String assignee;
        String time;
        String tag;

        int IssueID;
        int IssuePriority;

        int intcommentID;
        int intcount;
        String text;
        String reaction;
        String user;
        String timestampp2;


        try {
            FileReader filereader = new FileReader("data.json");

            JSONObject rootJSON = (JSONObject) new JSONParser().parse(filereader);
            JSONArray dataList = (JSONArray) rootJSON.get("projects");

            /**
             * For loop to loop through all Projects
             */

            for (Object projectObj : dataList.toArray()) {

                JSONObject project = (JSONObject) projectObj;
                long pjid = (long) project.get("id");
                JSONArray issueList = (JSONArray) project.get("issues");
                projectID = (int) pjid;
                Project p = new Project(projectID);

                ArrayList <Issue> issuesinproject = new ArrayList<>();

                // int ct=0;

                for (Object issueObj : issueList.toArray()) {
                    JSONObject iss = (JSONObject) issueObj;

                    long idd = (long) iss.get("id");
                    title = (String) iss.get("title");
                    long priority = (long) iss.get("priority");
                    status = (String) iss.get("status");

                    //Convert datatype to fit in Iss ArrayList
                    IssueID = (int) idd;
                    IssuePriority = (int) priority;

                    JSONObject issues = (JSONObject) issueObj;
                    JSONArray isuTag = (JSONArray) issues.get("tag");
                    tag = (String) isuTag.get(0);

                    description = (String) iss.get("descriptionText");
                    createdBy = (String) iss.get("createdBy");
                    assignee = (String) iss.get("assignee");
                    long timestamp = (long) iss.get("timestamp");
                    // convert seconds to milliseconds
                    Date dt = new Date(timestamp * 1000);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                    //timezone reference
                    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

                    //Convert datatype to fit in Iss ArrayList
                    time = sdf.format(dt);

                    Issue IssuefromJSON = new Issue(IssueID, title, description, createdBy, assignee, tag, IssuePriority,status,time); //Create New Issue with data from JSON file

                    //Read Comments stored as JSON under Issues
                    JSONObject comments = (JSONObject) issueObj;
                    JSONArray commentArray = (JSONArray) comments.get("comments");
                    for (Object commentObject : commentArray.toArray()) {
                        JSONObject commentObj = (JSONObject) commentObject;

                        long commentID = (long) commentObj.get("comment_id");
                        intcommentID = (int) commentID;
                        text = (String) commentObj.get("text");

                        HashMap<String, Integer> hashMap= new HashMap();

                        JSONObject reactObject = (JSONObject) commentObject;
                        JSONArray reactArray = (JSONArray) reactObject.get("react");
                        for (Object reactObj : reactArray.toArray()) {
                            JSONObject reactionObj = (JSONObject) reactObj;

                            reaction = (String) reactionObj.get("reaction");
                            long count = (long) reactionObj.get("count");
                            intcount = (int) count;

                            hashMap.put(reaction,intcount);

                        }

                        long timestampp = (long) commentObj.get("timestamp");
                        user = (String) commentObj.get("user");
                        // convert seconds to milliseconds
                        Date dt2 = new Date(timestamp * 1000);
                        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                        //timezone reference
                        sdf2.setTimeZone(TimeZone.getTimeZone("GMT"));
                        timestampp2 = sdf.format(dt2);

                        //   Object[] GeneratedHashMap=Arrays.asList(hashMap).toArray();
                        Comment c = new Comment(projectID,IssueID,text,intcommentID,user,timestampp2, hashMap);
                        IssuefromJSON.issue_commentlist.add(c);



                    }
                    Iss.add(IssuefromJSON); // Add New Issue to ArrayList "Iss"; "Iss" is a static variable that will be overall updated //

                    issuesinproject.add(IssuefromJSON); //To call while creating Project object


                    // ct++;

                }
                p.setListofIssue(issuesinproject);
                Projects.add(p);
                


            }for (int i = 0; i < Projects.size(); i++) {
                System.out.println("\n~~~~~~~~~~");
                
            }

            for (int i = 0; i < Iss.size(); i++) {
               Iss.get(i).displayComment();
            }
        } catch (ParseException e) {
            //do smth
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    Integer ProjectID;
    public static ArrayList<Project> Projects=new ArrayList<>();
    //ArrayList<Project> cloneProjects=();
    Integer issueno;
    String issuetitle;
    String issuedescriptiontext;
    //String timestamp;
    String issuecreator;
    String issueassignee;
    // public static ArrayList<Comment> issuecommentlist; //Arraylist to store all comments related to that issue
    String issuetag;
    int[] issue_priority_range = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    int issuepriority;
    String issuestatus;

    private String commenttext;
    private int commentid;
    private int projectID;
    private int issueID;
    private String commentuser;
    private String reaction;
    public List<String> reactionrange = new ArrayList<>();

    //HashMap<String, Integer> hashMap= new HashMap();


    public static ArrayList<Issue> Iss = new ArrayList<>();

   

}