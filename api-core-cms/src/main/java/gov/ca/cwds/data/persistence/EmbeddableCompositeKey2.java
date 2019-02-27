package gov.ca.cwds.data.persistence;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.IdClass;

import gov.ca.cwds.data.persistence.cms.VarargPrimaryKey;

/**
 * Hibernate "embeddable" class for composite primary keys. For use in entity classes.
 * 
 * <h2>Entity Class Usage:</h2>
 * 
 * <h3>Add an EmbeddableCompositeKey2 member to the entity class:</h3>
 *
 * <pre>
 * &#64;AttributeOverrides({
 *     &#64;AttributeOverride(name = "id1", column = @Column(name = "FKCLIENT_T", length = CMS_ID_LEN)),
 *     &#64;AttributeOverride(name = "id2", column = @Column(name = "THIRD_ID", length = CMS_ID_LEN))})
 * &#64;EmbeddedId
 * private EmbeddableCompositeKey2 id;
 * </pre>
 *
 * <h3>Create the primary key object in the entity constructors:</h3>
 *
 * <pre>
 * public OtherClientName() {
 *   super();
 *   this.id = new EmbeddableCompositeKey2();
 * }
 * 
 * public OtherClientName(String clientId, String firstName, String lastName, String middleName,
 * String namePrefixDescription, Short nameType, String suffixTitleDescription, String thirdId) {
 * super();
 * this.id = new EmbeddableCompositeKey2(clientId, thirdId);
 * this.firstName = firstName;
 * </pre>
 *
 * <h3>Create delegate getters/setters and set appropriate JSON field names on those methods:</h3>
 * 
 * <pre>
 * &#64;Override
 * public EmbeddableCompositeKey2 getPrimaryKey() {
 *   return this.id;
 * }
 * 
 * &#64;JsonProperty(value = "client_id")
 * public String getClientId() {
 *   return StringUtils.trimToEmpty(id.getId1());
 * }
 * 
 * &#64;JsonProperty(value = "third_id")
 * public String getThirdId() {
 *   return StringUtils.trimToEmpty(id.getId2());
 * }
 * 
 * &#64;JsonProperty(value = "client_id")
 * public void setClientId(String clientId) {
 *   id.setId1(clientId);
 * }
 * 
 * &#64;JsonProperty(value = "third_id")
 * public void setThirdId(String thirdId) {
 *   id.setId2(thirdId);
 * }
 * </pre>
 *
 * <h3>Update equals and hashCode methods:</h3>
 *
 * <pre>
 *   &#64;Override
 *    public final int hashCode() {
 *      final int prime = 31;
 *      int ret = 1;
 *      ret = prime * ret + ((id == null || id.getId1() == null) ? 0 : id.getId1().hashCode());
 *      ret = prime * ret + ((id == null || id.getId2() == null) ? 0 : id.getId2().hashCode());
 *      ret = prime * ret + ((firstName == null) ? 0 : firstName.hashCode());
 * </pre>
 * 
 * <h2>Alternative Approaches:</h2>
 * 
 * <p>
 * Prefer the embeddable approach over Hibernate annotation {@link IdClass} due to usage
 * constraints. {@link IdClass} requires that members match the id columns of the parent class. From
 * the Javadoc of said annotation,
 * </p>
 * 
 * <blockquote> "The names of the fields or properties in the primary key class and the primary key
 * fields or properties of the entity must correspond and their types must be the same."
 * </blockquote>
 * 
 * <p>
 * Instead of {@link IdClass}, use the nifty approach below using {@link Embeddable} and
 * {@link EmbeddedId}. Try it on your friends!
 * </p>
 * 
 * @see VarargPrimaryKey
 */
@Embeddable
public class EmbeddableCompositeKey2 extends CompositeKey implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Default constructor.
   */
  public EmbeddableCompositeKey2() {
    // Default, empty values for all key columns.
  }

  /**
   * Construct from arguments.
   * 
   * @param id1 generic id 1
   * @param id2 generic id 2
   */
  public EmbeddableCompositeKey2(String id1, String id2) {
    super(id1, id2);
  }

}
