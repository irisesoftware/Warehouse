package in.irise.soft.generator;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class MyGrnIdGen  implements IdentifierGenerator{

	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		String prefix="GRN-";
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyyhhmmssSSS");
		String dte = sdf.format(new Date());
		return prefix+dte;
	}
}
