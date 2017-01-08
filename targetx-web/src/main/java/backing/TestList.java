package backing;


import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name="testList")
@ViewScoped
public class TestList {

	@PostConstruct
	public void testListInit(){
		
	}
	
	
}
