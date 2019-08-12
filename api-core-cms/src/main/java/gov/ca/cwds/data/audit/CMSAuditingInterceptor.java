package gov.ca.cwds.data.audit;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.security.realm.PerrySubject;

// hibernate.session_factory.interceptor
public class CMSAuditingInterceptor extends EmptyInterceptor {

  @Override
  public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames,
      Type[] types) {
    if (entity instanceof CmsPersistentObject) {
      CmsPersistentObject cmsPersistentObject = (CmsPersistentObject) entity;
      cmsPersistentObject.setLastUpdateTime(LocalDateTime.now());
      String staffId = PerrySubject.getPerryAccount().getStaffId();
      cmsPersistentObject.setLastUpdateId(staffId);
      return true;
    }

    return false;
  }

}
