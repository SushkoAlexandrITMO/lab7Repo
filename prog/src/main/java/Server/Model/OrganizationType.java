package Server.Model;

import javax.xml.bind.annotation.*;

/**
 * OrganizationType - набор констант для описания типа организации
 */
@XmlType(name = "organizationType")
@XmlEnum
public enum OrganizationType {
    @XmlEnumValue("Commercial") COMMERCIAL,
    @XmlEnumValue("Government") GOVERNMENT,
    @XmlEnumValue("Trust") TRUST,
    @XmlEnumValue("Private Limited Company") PRIVATE_LIMITED_COMPANY;
}
