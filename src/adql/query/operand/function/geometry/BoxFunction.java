package adql.query.operand.function.geometry;

/*
 * This file is part of ADQLLibrary.
 *
 * ADQLLibrary is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ADQLLibrary is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with ADQLLibrary.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2012-2019 - UDS/Centre de Données astronomiques de Strasbourg (CDS),
 *                       Astronomisches Rechen Institut (ARI)
 */

import adql.parser.feature.LanguageFeature;
import adql.query.ADQLObject;
import adql.query.operand.ADQLOperand;

/**
 * It represents the box function of the ADQL language.
 *
 * <p>
 * 	The BOX function expresses a box on the sky. A BOX is a special case of
 * 	POLYGON, defined purely for convenience, and it corresponds semantically to
 * 	the equivalent term, Box, defined in the STC specification.
 * </p>
 *
 * <p>
 * 	It is specified by a center position and size (in both axes) defining a
 * 	cross centered on the center position and with arms extending, parallel to
 * 	the coordinate axes at the center position, for half the respective sizes on
 * 	either side. The box’s sides are line segments or great circles intersecting
 * 	the arms of the cross in its end points at right angles with the arms.
 * </p>
 *
 * <p>
 * 	The function arguments specify the center position and the width and height,
 * 	where:
 * </p>
 * <ul>
 * 	<li>the center position is given by a pair of numeric coordinates in
 * 		degrees, or a single geometric POINT</li>
 * 	<li>the width and height are given by numeric values in degrees</li>
 * 	<li>the center position and the width and height MUST be within the ranges
 * 		resp. [0,360] and [-90,90].</li>
 * </ul>
 *
 * <i>
 * <p><b>Example:</b></p>
 * <p>
 * 	A BOX of ten degrees centered on a position (25.4, -20.0) in degrees could
 * 	be written as follows:
 * </p>
 * <pre>BOX(25.4, -20.0, 10.0, 10.0)</pre>
 * <p>Alternatively, the center position could be expressed as a POINT:</p>
 * <pre>BOX(POINT(25.4, -20.0), 10, 10)</pre>
 * </i>
 *
 * <p>
 * 	The function arguments may be literal values, as above, or they may be
 * 	column references, functions or expressions that returns the appropriate
 * 	datatypes.
 * </p>
 *
 * <i>
 * <p><b>Example:</b></p>
 * <pre>BOX(t1.center, t1.width, t1.height)</pre>
 * <p>
 * 	, where t1.center, t1.width and t1.height are references to database columns
 * 	that contain POINT, DOUBLE and DOUBLE values respectively.
 * </p>
 * </i>
 *
 * <p>
 * 	For historical reasons, the BOX function accepts an optional string value as
 * 	the first argument. As of this version of the specification (2.1) this
 * 	parameter has been marked as deprecated. Future versions of this
 * 	specification (> 2.1) may remove this parameter.
 * </p>
 *
 * @author Gr&eacute;gory Mantelet (CDS;ARI)
 * @version 2.0 (07/2019)
 */
public class BoxFunction extends GeometryFunction {

	/** Description of this ADQL Feature.
	 * @since 2.0 */
	public static final LanguageFeature FEATURE = new LanguageFeature(LanguageFeature.TYPE_ADQL_GEO, "BOX", true, "Express a box on the sky.");

	/** The first coordinate of the center of this box. */
	private ADQLOperand coord1;

	/** The second coordinate of the center of this box. */
	private ADQLOperand coord2;

	/** The width of this box (in degrees). */
	private ADQLOperand width;

	/** The height of this box (in degrees). */
	private ADQLOperand height;

	/**
	 * Builds a BOX function.
	 *
	 * @param coordinateSystem		The coordinate system of the center
	 *                        		position.
	 * @param firstCoord			The first coordinate of the center of this
	 *                  			box.
	 * @param secondCoord			The second coordinate of the center of this
	 *                   			box.
	 * @param boxWidth				The width of this box (in degrees).
	 * @param boxHeight				The height of this box (in degrees).
	 * @throws NullPointerException	If one parameter is NULL.
	 * @throws Exception 			If there is another error.
	 */
	public BoxFunction(ADQLOperand coordinateSystem, ADQLOperand firstCoord, ADQLOperand secondCoord, ADQLOperand boxWidth, ADQLOperand boxHeight) throws NullPointerException, Exception {
		super(coordinateSystem);

		if (firstCoord == null || secondCoord == null || boxWidth == null || boxHeight == null)
			throw new NullPointerException("All the parameters of the BOX function must be different from NULL!");

		coord1 = firstCoord;
		coord2 = secondCoord;
		width = boxWidth;
		height = boxHeight;
	}

	/**
	 * Builds a BOX function by copying the given one.
	 *
	 * @param toCopy		The BOX function to copy.
	 * @throws Exception	If there is an error during the copy.
	 */
	public BoxFunction(BoxFunction toCopy) throws Exception {
		super(toCopy);
		coord1 = (ADQLOperand)(toCopy.coord1.getCopy());
		coord2 = (ADQLOperand)(toCopy.coord2.getCopy());
		width = (ADQLOperand)(toCopy.width.getCopy());
		height = (ADQLOperand)(toCopy.height.getCopy());
	}

	@Override
	public final LanguageFeature getFeatureDescription() {
		return FEATURE;
	}

	@Override
	public ADQLObject getCopy() throws Exception {
		return new BoxFunction(this);
	}

	@Override
	public String getName() {
		return "BOX";
	}

	@Override
	public boolean isNumeric() {
		return false;
	}

	@Override
	public boolean isString() {
		return false;
	}

	@Override
	public boolean isGeometry() {
		return true;
	}

	/**
	 * Gets the first coordinate (i.e. right ascension).
	 *
	 * @return The first coordinate.
	 */
	public final ADQLOperand getCoord1() {
		return coord1;
	}

	/**
	 * Sets the first coordinate (i.e. right ascension).
	 *
	 * @param coord1 The first coordinate.
	 */
	public final void setCoord1(ADQLOperand coord1) {
		this.coord1 = coord1;
		setPosition(null);
	}

	/**
	 * Gets the second coordinate (i.e. declination).
	 *
	 * @return The second coordinate.
	 */
	public final ADQLOperand getCoord2() {
		return coord2;
	}

	/**
	 * Sets the second coordinate (i.e. declination).
	 *
	 * @param coord2 The second coordinate.
	 */
	public final void setCoord2(ADQLOperand coord2) {
		this.coord2 = coord2;
		setPosition(null);
	}

	/**
	 * Gets the width of the box.
	 *
	 * @return The width.
	 */
	public final ADQLOperand getWidth() {
		return width;
	}

	/**
	 * Sets the width of the box.
	 *
	 * @param width The width.
	 */
	public final void setWidth(ADQLOperand width) {
		this.width = width;
		setPosition(null);
	}

	/**
	 * Gets the height of the box.
	 *
	 * @return The height.
	 */
	public final ADQLOperand getHeight() {
		return height;
	}

	/**
	 * Sets the height of the box.
	 *
	 * @param height The height.
	 */
	public final void setHeight(ADQLOperand height) {
		this.height = height;
		setPosition(null);
	}

	@Override
	public ADQLOperand[] getParameters() {
		return new ADQLOperand[]{ coordSys, coord1, coord2, width, height };
	}

	@Override
	public int getNbParameters() {
		return 5;
	}

	@Override
	public ADQLOperand getParameter(int index) throws ArrayIndexOutOfBoundsException {
		switch(index) {
			case 0:
				return coordSys;
			case 1:
				return coord1;
			case 2:
				return coord2;
			case 3:
				return width;
			case 4:
				return height;
			default:
				throw new ArrayIndexOutOfBoundsException("No " + index + "-th parameter for the function \"" + getName() + "\"!");
		}
	}

	@Override
	public ADQLOperand setParameter(int index, ADQLOperand replacer) throws ArrayIndexOutOfBoundsException, NullPointerException, Exception {
		if (replacer == null)
			throw new NullPointerException("Impossible to remove one parameter from a " + getName() + " function!");
		else if (!(replacer instanceof ADQLOperand))
			throw new Exception("Impossible to replace an ADQLOperand by a " + replacer.getClass().getName() + " (" + replacer.toADQL() + ")!");

		ADQLOperand replaced = null;
		switch(index) {
			case 0:
				replaced = coordSys;
				setCoordinateSystem(replacer);
				break;
			case 1:
				replaced = coord1;
				coord1 = replacer;
				break;
			case 2:
				replaced = coord2;
				coord2 = replacer;
				break;
			case 3:
				replaced = width;
				width = replacer;
				break;
			case 4:
				replaced = height;
				height = replacer;
				break;
			default:
				throw new ArrayIndexOutOfBoundsException("No " + index + "-th parameter for the function \"" + getName() + "\"!");
		}
		setPosition(null);
		return replaced;
	}

}
