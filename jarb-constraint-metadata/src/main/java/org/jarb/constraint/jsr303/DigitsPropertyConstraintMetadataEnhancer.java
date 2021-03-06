package org.jarb.constraint.jsr303;

import java.util.List;

import javax.validation.constraints.Digits;

import org.jarb.constraint.MutablePropertyConstraintMetadata;
import org.jarb.constraint.PropertyConstraintMetadataEnhancer;

/**
 * Enhance the property constraint descriptor with @Digits information.
 * 
 * @author Jeroen van Schagen
 * @since 31-05-2011
 */
public class DigitsPropertyConstraintMetadataEnhancer implements PropertyConstraintMetadataEnhancer {

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> MutablePropertyConstraintMetadata<T> enhance(MutablePropertyConstraintMetadata<T> propertyMetadata, Class<?> beanClass) {
        List<Digits> digitsAnnotations = ConstraintAnnotationScanner.getPropertyAnnotations(beanClass, propertyMetadata.getName(), Digits.class);
        Integer maximumLength = propertyMetadata.getMaximumLength();
        Integer fractionLength = propertyMetadata.getFractionLength();
        for(Digits digitsAnnotation : digitsAnnotations) {
            if (maximumLength != null) {
                // Store the lowest maximum length, as this will cause both lenght restrictions to pass
                maximumLength = Math.min(maximumLength, digitsAnnotation.integer());
            } else {
                maximumLength = digitsAnnotation.integer();
            }
            if (fractionLength != null) {
                // Store the lowest fraction length, as this will cause both lenght restrictions to pass
                fractionLength = Math.min(fractionLength, digitsAnnotation.fraction());
            } else {
                fractionLength = digitsAnnotation.fraction();
            }
        }
        propertyMetadata.setMaximumLength(maximumLength);
        propertyMetadata.setFractionLength(fractionLength);
        return propertyMetadata;
    }

}
