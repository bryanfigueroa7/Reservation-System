public class Sits {
    private String section;
    private int row;
    private int sitNum;

    //Constructor
    public Sits (String section , int row, int sitNum){
        this.section = section;
        this.row = row;
        this.sitNum = sitNum;
    }

    //Getters
    public String getSection(){
        return this.section;
    }
    public int getRow(){
        return this.row;
    }
    public int getSitNum(){
        return this.row;
    }

    //Setters
    public void setSection(String section){
        this.section = section;
    }
    public void setRow(int row){
        this.row = row;
    }
    public void setSitNum(int sitNum){
        this.sitNum = sitNum;
    }

}
