package at.fpmedv.util.regexp;

import java.util.regex.Pattern;

/**
 * Representation of a RegExp with some additional functionality.
 * You can store a replacement String and a boolean value for enabling and disabling
 * 
 * @author Franz Philipp Moser
 *
 */
public class RegExpReplacementContainer {

	/**
	 * hold the String of the regexp
	 */
	private String regexp;
	
	/**
	 * holds the String of the replacement
	 */
	private String replacement;
	
	/**
	 * if the RegExp is enabled
	 */
	private boolean enabled;
	
	/**
	 * if enabled my be set to false
	 */
	private boolean mayBeDisabled;
	
	/**
	 * creates a new RegExpContainer object
	 * 
	 * @param regexp String representation of the regexp
	 * @param replacement String representation of the replacement
	 * @param enabled if the regexp is enabled or not
	 * @param mayBeDisabled if this replacement may be disabled
	 */
	public RegExpReplacementContainer(String regexp, String replacement, boolean enabled, boolean mayBeDisabled) {
		setValues(regexp, replacement, enabled, mayBeDisabled);
	}

	/**
	 * creates a new RegExpContainer object sets mayBeDisabled to true
	 * 
	 * @param regexp String representation of the regexp
	 * @param replacement String representation of the replacement
	 * @param enabled if the regexp is enabled or not
	 */
	public RegExpReplacementContainer(String regexp, String replacement, boolean enabled) {
		setValues(regexp, replacement, enabled, true);
	}

	/**
	 * creates a new RegExpContainer object sets enabled, mayBeDisabled to true
	 * 
	 * @param regexp String representation of the regexp
	 * @param replacement String representation of the replacement
	 */
	public RegExpReplacementContainer(String regexp, String replacement) {
		setValues(regexp, replacement, true, true);
	}
	
	/**
	 * just a setter to set all values at once
	 * 
	 * @param regexp String representation of the regexp
	 * @param replacement String representation of the replacement
	 * @param enabled if the regexp is enabled or not
	 * @param mayBeDisabled if this replacement may be disabled
	 */
	private void setValues(String regexp, String replacement, boolean enabled, boolean mayBeDisabled) {
		this.setRegexp(regexp);
		this.setReplacement(replacement);
		this.setEnabled(enabled);
		this.setMayBeDisabled(mayBeDisabled);
	}
	/**
	 * just a getter
	 * 
	 * @return String representation of the regexp
	 */
	public String getRegexp() {
		return regexp;
	}

	/**
	 * tests if the regexp is valid and then stores it
	 * 
	 * @param regexp String representation of the regexp
	 */
	public void setRegexp(String regexp) {
		// test if regexp is valid
		Pattern.compile(regexp);
		this.regexp = regexp;
	}

	/**
	 * just a getter
	 * 
	 * @return String representation of the replacement
	 */
	public String getReplacement() {
		return replacement;
	}

	/**
	 * just a setter
	 * 
	 * @param replacement String representation of the replacement
	 */
	public void setReplacement(String replacement) {
		this.replacement = replacement;
	}

	/**
	 * just a getter
	 * 
	 * @return if the regexp is enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * just a setter
	 * 
	 * @param enabled enable or disable regexp
	 */
	public void setEnabled(boolean enabled) {
		// only set to false if may be disabled true
		if (!enabled && mayBeDisabled)
			this.enabled = false;
		// Allways set to true
		if (enabled)
			this.enabled = true;
	}
	
	/**
	 * disables the regexp by setting enabled to false
	 */
	public void disable() {
		if (mayBeDisabled)
			this.setEnabled(false);
	}

	/**
	 * just a getter
	 * 
	 * @return returns if this regexpreplacement may be disabled or not
	 */
	public boolean mayBeDisabled() {
		return mayBeDisabled;
	}

	/**
	 * just a setter
	 * 
	 * @param mayBeDisabled if {@link #enabled} may be set to false
	 */
	public void setMayBeDisabled(boolean mayBeDisabled) {
		this.mayBeDisabled = mayBeDisabled;
	}	
}
