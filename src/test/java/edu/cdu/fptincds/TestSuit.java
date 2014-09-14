package edu.cdu.fptincds;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import edu.cdu.fptincds.alg.AlgorithmUtilTest;
import edu.cdu.fptincds.alg.DSGreedyTest;
import edu.cdu.fptincds.alg.DomVCFPTTest;
import edu.cdu.fptincds.alg.IncDSFPTTest;
import edu.cdu.fptincds.alg.VCGreedyTest;
import edu.cdu.fptincds.io.FileOperationTest;


/**
 * A suite to accommodate all available tests, which will be called in test
 * runner
 * 
 * @author Kai Wang
 * 
 * 
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({ FileOperationTest.class,AlgorithmUtilTest.class,DSGreedyTest.class,VCGreedyTest.class,DomVCFPTTest.class,IncDSFPTTest.class })
public class TestSuit {

}
