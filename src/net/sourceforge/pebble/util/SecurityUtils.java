/*
 * Copyright (c) 2003-2006, Simon Brown
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in
 *     the documentation and/or other materials provided with the
 *     distribution.
 *
 *   - Neither the name of Pebble nor the names of its contributors may
 *     be used to endorse or promote products derived from this software
 *     without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package net.sourceforge.pebble.util;

import net.sourceforge.pebble.Constants;
import net.sourceforge.pebble.domain.Blog;
import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.TestingAuthenticationToken;
import org.acegisecurity.providers.encoding.Md5PasswordEncoder;
import org.acegisecurity.providers.encoding.ShaPasswordEncoder;
import org.acegisecurity.providers.encoding.PlaintextPasswordEncoder;
import org.acegisecurity.providers.encoding.PasswordEncoder;
import org.springframework.context.ApplicationContext;

/**
 * A collection of utility methods for security.
 *
 * @author    Simon Brown
 */
public final class SecurityUtils {

  public static String getUsername() {
    SecurityContext ctx = SecurityContextHolder.getContext();
    Authentication auth = ctx.getAuthentication();
    if (auth != null) {
      return auth.getName();
    } else {
      return null;
    }
  }

  public static boolean isUserInRole(String role) {
    SecurityContext ctx = SecurityContextHolder.getContext();
    Authentication auth = ctx.getAuthentication();
    if (auth != null) {
      GrantedAuthority[] authorities = auth.getAuthorities();
      if (authorities != null) {
        for (GrantedAuthority authority : authorities) {
          if (authority.getAuthority().equals(role)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * Determines whether this user is a Pebble admin user.
   *
   * @return  true if the user is a Pebble admin, false otherwise
   */
  public static boolean isPebbleAdmin() {
    return isUserInRole(Constants.PEBBLE_ADMIN_ROLE);
  }

  /**
   * Determines whether this user is a blog owner.
   *
   * @return  true if the user is a blog owner, false otherwise
   */
  public static boolean isBlogOwner() {
    return isUserInRole(Constants.BLOG_OWNER_ROLE);
  }

  /**
   * Determines whether this user is a blog contributor.
   *
   * @return  true if the user is a blog contributor, false otherwise
   */
  public static boolean isBlogContributor() {
    return isUserInRole(Constants.BLOG_CONTRIBUTOR_ROLE);
  }

  public static void runAsBlogOwner() {
    Authentication auth = new TestingAuthenticationToken("username", "password", new GrantedAuthority[] {new GrantedAuthorityImpl(Constants.BLOG_OWNER_ROLE)});
    SecurityContextHolder.getContext().setAuthentication(auth);
  }

  public static void runAsBlogContributor() {
    Authentication auth = new TestingAuthenticationToken("username", "password", new GrantedAuthority[] {new GrantedAuthorityImpl(Constants.BLOG_CONTRIBUTOR_ROLE)});
    SecurityContextHolder.getContext().setAuthentication(auth);
  }

  public static void runAsAnonymous() {
    Authentication auth = new TestingAuthenticationToken("username", "password", new GrantedAuthority[] {});
    SecurityContextHolder.getContext().setAuthentication(auth);
  }

  public static boolean isUserAuthorisedForBlogAsBlogOwner(Blog blog) {
    String currentUser = SecurityUtils.getUsername();
    return isBlogOwner() && blog.isUserInRole(Constants.BLOG_OWNER_ROLE, currentUser);
  }

  public static boolean isUserAuthorisedForBlogAsBlogContributor(Blog blog) {
    String currentUser = SecurityUtils.getUsername();
    return isBlogContributor() && blog.isUserInRole(Constants.BLOG_CONTRIBUTOR_ROLE, currentUser);
  }

  public static boolean isUserAuthenticated() {
    SecurityContext ctx = SecurityContextHolder.getContext();
    return ctx.getAuthentication() != null;
  }

  public static void main(String[] args) {
    if (args.length != 3) {
      System.out.println("Usage : [md5|sha|plaintext] username password");
    } else if (args[0].equals("md5")) {
      PasswordEncoder encoder = new Md5PasswordEncoder();
      System.out.println(encoder.encodePassword(args[2], args[1]));
    } else if (args[0].equals("sha")) {
      PasswordEncoder encoder = new ShaPasswordEncoder();
      System.out.println(encoder.encodePassword(args[2], args[1]));
    } else if (args[0].equals("plaintext")) {
      PasswordEncoder encoder = new PlaintextPasswordEncoder();
      System.out.println(encoder.encodePassword(args[2], args[1]));
    } else {
      System.out.println("Algorithm must be md5, sha or plaintext");
    }
  }

}