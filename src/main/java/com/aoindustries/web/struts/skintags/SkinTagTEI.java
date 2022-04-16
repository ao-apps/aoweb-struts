/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2009, 2016, 2019, 2020, 2021, 2022  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of aoweb-struts.
 *
 * aoweb-struts is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aoweb-struts is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with aoweb-struts.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.aoindustries.web.struts.skintags;

import com.aoapps.encoding.Doctype;
import com.aoapps.encoding.Serialization;
import com.aoapps.taglib.HtmlTag;
import com.aoindustries.web.struts.Constants;
import com.aoindustries.web.struts.Formtype;
import static com.aoindustries.web.struts.Resources.PACKAGE_RESOURCES;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.ValidationMessage;

/**
 * @author  AO Industries, Inc.
 */
public class SkinTagTEI extends TagExtraInfo {

	@Override
	public ValidationMessage[] validate(TagData data) {
		List<ValidationMessage> messages = new ArrayList<>();
		Object serializationAttr = data.getAttribute("serialization");
		if(
			serializationAttr != null
			&& serializationAttr != TagData.REQUEST_TIME_VALUE
		) {
			String serialization = ((String)serializationAttr).trim(); // TODO: normalizeSerialization
			if(!serialization.isEmpty() && !"auto".equalsIgnoreCase(serialization)) {
				try {
					Serialization.valueOf(serialization.toUpperCase(Locale.ROOT));
				} catch(IllegalArgumentException e) {
					messages.add(
						new ValidationMessage(
							data.getId(),
							HtmlTag.RESOURCES.getMessage("serialization.invalid", serialization)
						)
					);
				}
			}
		}
		Object doctypeAttr = data.getAttribute("doctype");
		if(
			doctypeAttr != null
			&& doctypeAttr != TagData.REQUEST_TIME_VALUE
		) {
			String doctype = ((String)doctypeAttr).trim(); // TODO: normalizeDoctype
			if(!doctype.isEmpty() && !"default".equalsIgnoreCase(doctype)) {
				try {
					Doctype.valueOf(doctype.toUpperCase(Locale.ROOT));
				} catch(IllegalArgumentException e) {
					messages.add(
						new ValidationMessage(
							data.getId(),
							HtmlTag.RESOURCES.getMessage("doctype.invalid", doctype)
						)
					);
				}
			}
		}
		Object autonliAttr = data.getAttribute("autonli");
		if(
			autonliAttr != null
			&& autonliAttr != TagData.REQUEST_TIME_VALUE
		) {
			String autonli = ((String)autonliAttr).trim(); // TODO: normalizeAutonli
			if(
				!autonli.isEmpty()
				&& !"auto".equalsIgnoreCase(autonli)
				&& !"true".equalsIgnoreCase(autonli)
				&& !"false".equalsIgnoreCase(autonli)
			) {
				messages.add(
					new ValidationMessage(
						data.getId(),
						HtmlTag.RESOURCES.getMessage("autonli.invalid", autonli)
					)
				);
			}
		}
		Object indentAttr = data.getAttribute("indent");
		if(
			indentAttr != null
			&& indentAttr != TagData.REQUEST_TIME_VALUE
		) {
			String indent = ((String)indentAttr).trim(); // TODO: normalizeIndent
			if(
				!indent.isEmpty()
				&& !"auto".equalsIgnoreCase(indent)
				&& !"true".equalsIgnoreCase(indent)
				&& !"false".equalsIgnoreCase(indent)
			) {
				messages.add(
					new ValidationMessage(
						data.getId(),
						HtmlTag.RESOURCES.getMessage("indent.invalid", indent)
					)
				);
			}
		}
		Object layoutAttr = data.getAttribute(Constants.LAYOUT.getName());
		if(
			layoutAttr != null
			&& layoutAttr != TagData.REQUEST_TIME_VALUE
		) {
			String layout = ((String)layoutAttr).trim();
			if(!PageAttributes.LAYOUT_NORMAL.equals(layout) && !PageAttributes.LAYOUT_MINIMAL.equals(layout)) {
				messages.add(
					new ValidationMessage(
						data.getId(),
						PACKAGE_RESOURCES.getMessage(
							//"Invalid value for layout, must be either \"normal\" or \"minimal\"",
							//Locale.getDefault(),
							"skintags.SkinTagTEI.validate.layout.invalid"
						)
					)
				);
			}
		}
		Object formtypeAttr = data.getAttribute("formtype");
		if(
			formtypeAttr != null
			&& formtypeAttr != TagData.REQUEST_TIME_VALUE
		) {
			String formtype = ((String)formtypeAttr).trim(); // TODO: normalizeFormtype
			if(!formtype.isEmpty() && !"none".equalsIgnoreCase(formtype)) {
				try {
					Formtype.valueOf(formtype.toUpperCase(Locale.ROOT));
				} catch(IllegalArgumentException e) {
					messages.add(
						new ValidationMessage(
							data.getId(),
							PACKAGE_RESOURCES.getMessage(
								"skintags.SkinTagTEI.validate.formtype.invalid"
							)
						)
					);
				}
			}
		}
		return messages.isEmpty() ? null : messages.toArray(new ValidationMessage[messages.size()]);
	}
}
