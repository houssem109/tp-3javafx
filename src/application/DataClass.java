package application;

import java.util.ArrayList;
import java.util.List;

public class DataClass {
	private List<Person> importList;
	private List<Person> exportList;
	
	public DataClass() {
		importList = new ArrayList<Person>();
		importList.add(new Person("BenAli","sami","sami@example.com"));
		importList.add(new Person("BenFlen","alia","alia@example.com"));
		importList.add(new Person("BenSalah","ali","ali@example.com"));
        exportList = new ArrayList<Person>();

	}

	public List<Person> getImportList() {
		return importList;
	}

	public List<Person> getExportList() {
		return exportList;
	}

	public void setExportList(List<Person> exportList) {
	
		this.exportList.addAll(exportList);
		for (Person p:this.exportList){
			System.out.println(p);
		}
	
	}
	
	
}
