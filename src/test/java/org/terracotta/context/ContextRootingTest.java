/*
 * All content copyright Terracotta, Inc., unless otherwise indicated.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terracotta.context;

import java.util.Collection;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;
import org.terracotta.context.ContextTestUtils.NoAnnotations;

import static org.hamcrest.collection.IsCollectionWithSize.*;
import static org.hamcrest.core.IsSame.*;
import static org.junit.Assert.assertThat;
import static org.terracotta.context.query.QueryBuilder.*;

/**
 *
 * @author cdennis
 */
public class ContextRootingTest {
  
  @Test
  public void testRootCreation() {
    ContextManager manager = new ContextManager();
    
    Object root = new NoAnnotations();
    manager.root(root);
    
    Collection<TreeNode> roots = manager.query(queryBuilder().children().build());

    assertThat(roots, hasSize(1));
    assertThat(roots.iterator().next().getContext().attributes().get("this"), sameInstance(root));
  }
  
  @Test
  public void testRootRemoval() {
    ContextManager manager = new ContextManager();
    
    Object root = new NoAnnotations();
    manager.root(root);
    manager.uproot(root);
    
    Collection<TreeNode> roots = manager.query(queryBuilder().children().build());

    assertThat(roots, IsEmptyCollection.<TreeNode>empty());
  }
  
  @Test
  public void testDoubleRootCreation() {
    ContextManager manager = new ContextManager();
    
    Object root = new NoAnnotations();
    manager.root(root);
    manager.root(root);
    
    Collection<TreeNode> roots = manager.query(queryBuilder().children().build());

    assertThat(roots, hasSize(1));
    assertThat(roots.iterator().next().getContext().attributes().get("this"), sameInstance(root));
  }

  @Test
  public void testDoubleRootRemoval() {
    ContextManager manager = new ContextManager();
    
    Object root = new NoAnnotations();
    manager.root(root);
    manager.root(root);
    manager.uproot(root);
    
    Collection<TreeNode> roots = manager.query(queryBuilder().children().build());

    assertThat(roots, IsEmptyCollection.<TreeNode>empty());
  }
}
