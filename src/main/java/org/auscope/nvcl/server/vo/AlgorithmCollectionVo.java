package org.auscope.nvcl.server.vo;


import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Algorithms")
public class AlgorithmCollectionVo {
	private ArrayList<AlgorithmVo> algorithms;

	
	public AlgorithmCollectionVo() {
		algorithms=new ArrayList<AlgorithmVo>();
	}
	public AlgorithmCollectionVo(ArrayList<AlgorithmVo> algorithms) {
		this.algorithms = algorithms;
	}
	public ArrayList<AlgorithmVo> getAlgorithms() {
		return algorithms;
	}
	@XmlElement(name = "algorithms")
	public void setAlgorithms(ArrayList<AlgorithmVo> algorithms) {
		this.algorithms = algorithms;
	}
	
	public void addAlgorithm(String name,int algid, String outputname, int outputid, int outputversion){
		AlgorithmVo alg=null;
		for (AlgorithmVo curalg : this.algorithms){
			if (curalg.getAlgorithmID()==algid) {
				alg =curalg;
				break;
			}
		}
		if (alg == null) {
			alg = new AlgorithmVo();
			alg.setName(name);
			alg.setAlgorithmID(algid);
			this.algorithms.add(alg);
		}
		AlgorithmOutputVo algouts=null;
		for (AlgorithmOutputVo curout : alg.getOutputs()){
			if(curout.getName().equals(outputname)){
				algouts = curout;
				break;
			}
		}
		if (algouts == null){
			algouts = new AlgorithmOutputVo(outputname);
			alg.getOutputs().add(algouts);
		}
		
		AlgorithmOutputVersionVo algoutver=null;
		for (AlgorithmOutputVersionVo curalgoutver : algouts.getVersions()){
			if(curalgoutver.getAlgorithmoutputID()==outputid){
				algoutver = curalgoutver;
				break;
			}
		}
		if (algoutver == null){
			algoutver = new AlgorithmOutputVersionVo(outputid,outputversion);
			algouts.getVersions().add(algoutver);
		}
		
	}
	
}
