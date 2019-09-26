
import java.util.*;

public class ListImpl<T> implements List<T> {

    private Item<T> firstInList = null;

    private Item<T> lastInList = null;

    private int size=0;

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean contains(final Object o) {
        // BEGIN (write your solution here)
        Item <T> newNextItem=firstInList;
        T newNextElement=newNextItem.element;

        for(int i=0;i<this.size();i++)
        {
            //if(o==null&&newNextElement==null) return true;
            if(newNextElement==o) return true;
            else {
                newNextItem = newNextItem.getNextItem();
                if(newNextItem!=null)
                {
                    newNextElement = newNextItem.element;
                }

            }
        }
        return false;
        // END
    }

    @Override
    public Iterator<T> iterator() {
        return new ElementsIterator(0);
    }

    @Override
    public Object[] toArray() {
        // BEGIN (write your solution here)
        final T[] newM = (T[]) new Object[this.size()];
        Item<T> newNextItem=firstInList;

        for(int i=0;i<this.size();i++)
        {
            newM[i]=newNextItem.element;
            newNextItem=newNextItem.getNextItem();
        }
        return newM;
        // END
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        // BEGIN (write your solution here)
        Object[] newM = toArray();
        if (a.length < this.size) return (T1[]) Arrays.copyOf(newM, size, a.getClass());

        System.arraycopy(newM, 0, a, 0, size);

        if (a.length > size) a[size] = null;

        return a;
        // END
    }


    void rebaseLinkItem(Item<T> currentItem) {

        if(currentItem.prevItem==null&&currentItem.nextItem==null)
        {
            currentItem.element=null;
            lastInList= null;
            firstInList= null;

        }

        else if(currentItem.prevItem==null)
        {
            Item<T> nextForRebase=currentItem.getNextItem();
            nextForRebase.prevItem=null;
            firstInList= nextForRebase;


        }
        else if(currentItem.nextItem==null)
        {

            Item<T> previousForRebase=currentItem.getPrevItem();
            previousForRebase.nextItem=null;
            lastInList=previousForRebase;
        }
        else
        {
            Item<T> previousForRebase=currentItem.getPrevItem();
            Item<T> nextForRebase=currentItem.getNextItem();
            previousForRebase.nextItem=nextForRebase;
            nextForRebase.prevItem=previousForRebase;
        }
    }


    @Override
    public boolean add(final T newElement) {
        // BEGIN (write your solution here)
        linkLast(newElement);
        return true;
        // END
    }

    @Override
    public boolean remove(final Object o) {
        // BEGIN (write your solution here)
        if(this.contains(o))
        {
            Item<T> currentItem=firstInList;
            for(int i=0;i<this.size();i++)
            {
                if(currentItem.element.equals(o))
                {
                    rebaseLinkItem(currentItem);
                    size--;
                    break;
                }
                else currentItem=currentItem.nextItem;
            }
            return true;
        }
        else return false;
        // END
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        for (final Object item : c) {
            if (!this.contains(item)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(final Collection<? extends T> c) {
        for (final T item : c) {
            add(item);
        }
        return true;
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        for (final Object item : c) {
            remove(item);
        }
        return true;
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        for (final T item : this) {

            if (!c.contains(item))
                this.remove(item);
        }
        return true;
    }

    @Override
    public void clear() {
        // BEGIN (write your solution here)
        this.lastInList=null;
        this.firstInList=null;
        size=0;

        // END
    }

    @Override
    public T remove(final int index) throws IndexOutOfBoundsException{
        // BEGIN (write your solution here)

        if (index < 0 || index >= size())  throw new IndexOutOfBoundsException();

        Item<T> currentItem=firstInList;

        for (int i=0;i<this.size();i++)
        {
            if(i==index)
            {
                rebaseLinkItem(currentItem);
                size--;
                break;
            }
            else currentItem=currentItem.nextItem;

        }
        return currentItem.element;
        // END
    }


    private void remove(final Item<T> current) {
        // BEGIN (write your solution here)
        rebaseLinkItem(current);
        size--;
        // END
    }

    @Override
    public List<T> subList(final int start, final int end) throws IndexOutOfBoundsException ,IllegalArgumentException{

        if (start < 0 || end > size)  throw new  IndexOutOfBoundsException();
        if (start > size)  throw new  IllegalArgumentException();

        List<T> newList =new ArrayList<>();
        Item<T> currentItem=firstInList;
        int i=1;
        while(i<start)
        {
            currentItem= currentItem.getNextItem();
            i++;
        }

        for(int k=start;k<end;k++)
        {

            newList.add(currentItem.element);
            currentItem= currentItem.getNextItem();

        }

        return newList;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ElementsIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(final int index) {
        return new ElementsIterator(index);
    }

    @Override
    public int lastIndexOf(final Object target) {
        Item<T> currentItem=firstInList;

        for (int i=this.size();i!=0;i--)
        {
            if(currentItem.element.equals(target))
            {
                return i;
            }
            else currentItem=currentItem.nextItem;

        }
        return -1;
    }

    @Override
    public int indexOf(final Object o) {
        // BEGIN (write your solution here)
        Item<T> currentItem=firstInList;

        for (int i=0;i<this.size();i++)
        {
            if(currentItem.element.equals(o))
            {
                return i;
            }
            else currentItem=currentItem.nextItem;

        }
        return -1;
        // END
    }


    void linkLast(T e) {
        final Item<T> l = lastInList;
        final Item<T> newNode = new Item<>(e, l, null);
        lastInList = newNode;
        if (l == null)
            firstInList = newNode;
        else
            l.nextItem = newNode;
        size++;
    }
    void linkBefore(T e, Item<T> succ) {
        final Item<T> pred = succ.prevItem;
        final Item<T> newNode = new Item<>(e,pred, succ);
        succ.prevItem = newNode;
        if (pred == null)
            firstInList = newNode;
        else
            pred.nextItem = newNode;
        size++;
    }


    @Override
    public void add(final int index, final T element) throws IndexOutOfBoundsException{
        if(index < 0 || index > size()) throw new  IndexOutOfBoundsException();

        if (index == size)
            linkLast(element);
        else
            linkBefore(element, this.getItemByIndex(index));

    }

    @Override
    public boolean addAll(final int index, final Collection<? extends T> elements) throws IndexOutOfBoundsException,NullPointerException{

        if(index < 0 || index > size()) throw new  IndexOutOfBoundsException();
        if(elements==null) throw new  NullPointerException();
        int i=index;

        for(T item:elements)
        {
            this.add(i,item);
            i++;
        }
        return true;
    }

    @Override
    public T set(final int index, final T element) throws IndexOutOfBoundsException{
        // BEGIN (write your solution here)
        if(index < 0 || index >= size()) throw new IndexOutOfBoundsException();

        Item<T> currentItem=firstInList;
        T replacedItem=null;


        for (int i=0;i<this.size();i++)
        {
            if(i==index)
            {
                replacedItem=currentItem.element;
                currentItem.element=element;
            }
            else currentItem=currentItem.nextItem;

        }
        return replacedItem;
        // END
    }

    @Override
    public T get(final int index) {
        // BEGIN (write your solution here)
        return getItemByIndex(index).element;

        // END
    }

    private Item<T> getItemByIndex(final int index) {
        // BEGIN (write your solution here) An auxiliary method for searching for node by index.
        if(index < 0 || index >= size()) throw new IndexOutOfBoundsException();

        Item<T> currentItem=firstInList;

        for (int i=0;i<this.size();i++)
        {
            if(i==index)
            {
                break;
            }
            else currentItem=currentItem.nextItem;

        }
        return currentItem;
        // END
    }

    private class ElementsIterator implements ListIterator<T> {

        private Item<T> currentItemInIterator;

        private Item<T> lastReturnedItemFromIterator;

        private int index;

        ElementsIterator(final int index) {
            // BEGIN (write your solution here)
            this.index=index;
            currentItemInIterator = (index == size) ? null : ListImpl.this.getItemByIndex(index);
            //lastReturnedItemFromIterator=currentItemInIterator.getPrevItem();
            // END
        }

        @Override
        public boolean hasNext() {
            // BEGIN (write your solution here)
            return ListImpl.this.size() > index;

            // END
        }

        @Override
        public T next() {
            // BEGIN (write your solution here)
            if (!hasNext()) throw new NoSuchElementException();

            lastReturnedItemFromIterator = currentItemInIterator;
            currentItemInIterator=currentItemInIterator.nextItem;
            index++;
            return lastReturnedItemFromIterator.element;

            // END
        }

        @Override
        public boolean hasPrevious() {
            // BEGIN (write your solution here)
            return index!=0;//index
            // END
        }
        @Override
        public T previous() throws java.util.NoSuchElementException{
            // BEGIN (write your solution here)
            if (!hasPrevious()) throw new java.util.NoSuchElementException();

            lastReturnedItemFromIterator = currentItemInIterator = (currentItemInIterator == null) ? ListImpl.this.lastInList : currentItemInIterator.prevItem;
            index--;

            return lastReturnedItemFromIterator.element;
            // END
        }

        @Override
        public void add(final T element) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(final T element) {
            // BEGIN (write your solution here)
            // if(lastIndex<0) throw new IllegalStateException();
            if(index<0||element==null) throw new IllegalStateException();
            // currentItemInIterator.element=element;
            lastReturnedItemFromIterator.element=element;
            //ArrayList.this.add(lastIndex, element);
            // END
        }

        @Override
        public int previousIndex(){
            // BEGIN (write your solution here)
            if(index==0)
                return -1;
            else if(hasPrevious())
                return index-1;

            else  throw new java.util.NoSuchElementException();
            // END
        }
        @Override
        public int nextIndex() {
            // BEGIN (write your solution here)
            return index;

            // END
        }


        @Override
        public void remove() {
            // BEGIN (write your solution here)
            if (lastReturnedItemFromIterator == null) throw new IllegalStateException();
            ListImpl.this.remove(lastReturnedItemFromIterator);
            // currentItemInIterator=lastReturnedItemFromIterator;
            lastReturnedItemFromIterator=null;
            index--;
            // END
        }
    }

    private static class Item<T> {

        private T element;

        private Item<T> nextItem;

        private Item<T> prevItem;

        Item(final T element, final Item<T> prevItem, final Item<T> nextItem) {
            this.element = element;
            this.nextItem = nextItem;
            this.prevItem = prevItem;
        }


        public Item<T> getNextItem() {
            return nextItem;
        }

        public Item<T> getPrevItem() {
            return prevItem;
        }
    }
}
