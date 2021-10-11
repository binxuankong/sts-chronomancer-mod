package chronoMod.cards;

import chronoMod.ChronoMod;
import chronoMod.actions.RepeatAction;
import chronoMod.characters.Chronomancer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chronoMod.ChronoMod.makeCardPath;

public class Flashback extends AbstractDynamicCard {
    public static final String ID = ChronoMod.makeID(Flashback.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Chronomancer.Enums.COLOR_BLUE;

    private static final int COST = 2;
    private static final int DAMAGE = 14;
    private static final int UPGRADE_PLUS_DAMAGE = 4;

    public Flashback() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToTop(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        // AbstractPower jade = p.getPower(JadePower.POWER_ID);
        // if (jade != null) {
        //     this.addToBot(new ApplyPowerAction(p, p, new FlashbackPower(p, jade.amount), jade.amount));
        // this.addToBot(new RemoveSpecificPowerAction(p, p, jade));
        // }
        this.addToBot(new RepeatAction(1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            initializeDescription();
        }
    }

    @Override
    public AbstractDynamicCard makeCopy() {
        return new Flashback();
    }
}
